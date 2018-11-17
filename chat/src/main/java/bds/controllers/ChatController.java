package bds.controllers;

import bds.dao.MessagesRecord;
import bds.dao.UsersRecord;
import bds.dao.repo.MessagesRepository;
import bds.dao.repo.RolesRepository;
import bds.dao.repo.UsersRepository;
import bds.dto.MessageDTO;
import bds.model.RegistrationForm;
import bds.services.LoginExistsException;
import bds.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@PropertySource("classpath:/chat.properties")
@EnableJpaRepositories("bds.dao")
@EntityScan(basePackageClasses = {bds.dao.MessagesRecord.class, bds.dao.UsersRecord.class, bds.dao.RolesRecord.class, bds.dao.UserRoleRecord.class})
public class ChatController {


    @Value("${chat.maxMessagesOnThePage.prop}")
    private String maxMessagesOnThePage;

    @Value("${chat.pageTickTime.prop}")
    private String pageTickTime;


    private static final Logger LOG = LoggerFactory.getLogger(ChatController.class);

    static private List<UsersRecord> allUsers;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    UsersRepository usersRepository;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    MessagesRepository messagesRepository;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String viewRegistrationPage(Model model) {

        RegistrationForm registrationForm = new RegistrationForm();

        model.addAttribute("registrationForm", registrationForm);

        return "/registrationpage";
    }

    @RequestMapping(value = "/chat", method = RequestMethod.GET)
    public String viewChat(Model model) {

        String login = "";

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            login = ((UserDetails) principal).getUsername();
        } else {
            login = principal.toString();
        }

        model.addAttribute("login", login);
        model.addAttribute("maxMessagesOnThePage", maxMessagesOnThePage);
        model.addAttribute("pageTickTime", pageTickTime);

        return "/chat";
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registrationAction(Model model, RegistrationForm registrationForm) {

        try {
            userService.registerNewUserAccount(registrationForm);
        } catch (LoginExistsException e) {

            String infoMessage = "Пользователь с таким логином уже есть";

            model.addAttribute("registrationForm", registrationForm);
            model.addAttribute("infoMessage", infoMessage);
            return "/registrationpage";

        }

        String infoMessage = "Зарегистрирован. Попробуйте войти";

        model.addAttribute("infoMessage", infoMessage);
        model.addAttribute("username", registrationForm.getUserName());

        return "/login";
    }


    // слушаем адрес /sendmessage, забираем объёкт сообщения, записываем в базу
    @RequestMapping(value = "/sendmessage", method = RequestMethod.POST,
            headers = "Accept=application/json", consumes = "application/json")
    @ResponseBody
    public String getMessage(@RequestBody final MessageDTO messageDTO) throws Exception {

        LOG.info("/sendmessage");
        LOG.info("got object: " + messageDTO.toString());

        MessagesRecord messagesRecord = new MessagesRecord();

        messagesRecord.setMessage(messageDTO.getMessage());

        allUsers = (List<UsersRecord>) usersRepository.findAll();

        if (allUsers.size() == 0) {
            ChatController.LOG.info("NO RECORDS");
            ChatController.LOG.info("{\"success\":\"false\";}");
            return "{\"success\":\"false\"}";
        }

        int foundId = 0;

        for (UsersRecord element : allUsers) {
            if (element.equals(new UsersRecord(messageDTO.getLogin()))) {
                foundId = element.getId();
            }
        }

        if (foundId == 0) {
            ChatController.LOG.info("!!!  login " + messageDTO.getLogin() + " not found in table users");
            ChatController.LOG.info("{success:\"false\";}");
            return "{\"success\":\"false\"}";
        }

        messagesRecord.setUserId(foundId);

        messagesRepository.save(messagesRecord);

        ChatController.LOG.info("recorded object \"" + messagesRecord.toString() + "\" " + " into database table \"messages\"");

        return "{\"success\":\"true\"}";

    }


    // слушаем адрес /getlatestmessages, забираем объёкт сообщения
    @RequestMapping(value = "/getlatestmessages", method = RequestMethod.POST,
            headers = "Accept=application/json", consumes = "application/json")
    @ResponseBody
    public String sendLatestMessages(@RequestBody final MessageDTO messageDTO) throws Exception {

        List<MessageDTO> listToSend = null;

        LOG.info("/getlatestmessages");

        if (messageDTO != null) {

            String login = messageDTO.getLogin();

            if (!login.isEmpty()) {

                LOG.info("got object: " + login);
                LOG.info("/getlatestmessages : got request for login " + login);

                listToSend = userService.getLatestMessagesForUser(login);

            } else {
                LOG.info("/getlatestmessages  : got request with login");
            }

            ObjectMapper mapper = new ObjectMapper();
            String jsonListToSend = mapper.writeValueAsString(listToSend);
            LOG.info("/getlatestmessages  : sent to " + login + ":" + jsonListToSend);
            return jsonListToSend;
        } else {
            LOG.info("/getlatestmessages  : got request with empty request object");
        }

        ObjectMapper mapper = new ObjectMapper();
        String jsonListToSend = mapper.writeValueAsString(listToSend);
        LOG.info("/getlatestmessages  : sent : " + jsonListToSend);
        return mapper.writeValueAsString(listToSend);
    }

}
