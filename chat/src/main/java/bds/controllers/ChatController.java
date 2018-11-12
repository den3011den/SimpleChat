package bds.controllers;

import bds.RegistrationForm;
import bds.dao.MessagesRecord;
import bds.dao.RolesRecord;
import bds.dao.UsersRecord;
import bds.dao.repo.MessagesRepository;
import bds.dao.repo.UsersRepository;
import bds.dao.repo.RolesRepository;
import bds.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Controller
@PropertySource("classpath:/chat.properties")
@EnableJpaRepositories("bds.dao")
@EntityScan(basePackageClasses = {bds.dao.MessagesRecord.class, bds.dao.UsersRecord.class, bds.dao.RolesRecord.class, bds.dao.UserRoleRecord.class})
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    static private List<UsersRecord> allUsers;
    static private List<MessagesRecord> allMessages;
    static private List<RolesRecord> allRoles;

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


    public void deleteUsersRecord(UsersRecord usersRecord) {
        usersRepository.delete(usersRecord);
    }

    public void updateUsersRecord(UsersRecord usersRecord) {
        usersRepository.save(usersRecord);
    }

    public void readUsersRecordAll() {
        allUsers = (List<UsersRecord>) usersRepository.findAll();

        if (allUsers.size() == 0) {
            log.info("NO RECORDS");
        }

        allUsers.stream().forEach(userrecord -> log.info(userrecord.toString()));
    }

    public UsersRecord createUsersRecord(UsersRecord usersRecord) {
        return usersRepository.save(usersRecord);
    }


    public void deleteMessagesRecord(MessagesRecord messagesRecord) {
        messagesRepository.delete(messagesRecord);
    }

    public void updateMessagesRecord(MessagesRecord messagesRecord) {
        messagesRepository.save(messagesRecord);
    }

    public void readMessagesRecordAll() {
        allMessages = (List<MessagesRecord>) messagesRepository.findAll();

        if (allMessages.size() == 0) {
            log.info("NO RECORDS");
        }

        allMessages.stream().forEach(messagerecord -> log.info(messagerecord.toString()));
    }

    public MessagesRecord createMessagesRecord(MessagesRecord messagesRecord) {
        return messagesRepository.save(messagesRecord);
    }


    public void deleteRolesRecord(RolesRecord rolesRecord) {
        rolesRepository.delete(rolesRecord);
    }

    public void updateRolesRecord(RolesRecord rolesRecord) {
        rolesRepository.save(rolesRecord);
    }

    public void readRolesRecordAll() {
        allRoles = (List<RolesRecord>) rolesRepository.findAll();

        if (allRoles.size() == 0) {
            log.info("NO RECORDS");
        }

        allRoles.stream().forEach(rolerecord -> log.info(rolerecord.toString()));
    }

    public RolesRecord createRolesRecord(RolesRecord rolesRecord) {
        return rolesRepository.save(rolesRecord);
    }



    private UsersRecord createUserAccount(@Valid RegistrationForm registrationForm, BindingResult result) {
        UsersRecord registered = null;
        try{
            registered = userService.registerNewUserAccount(registrationForm);
//        }catch (LoginExistsException e) {
//            return null;
//        }

        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return registered;
    }



    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String viewTestPage(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Set<String> roles = authentication.getAuthorities().stream()
                .map(r -> r.getAuthority()).collect(Collectors.toSet());

        Iterator<String> iterator = roles.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.toString() + " " + iterator.next());
        }

        return "/error";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String viewRegistrationPage(Model model) {

        RegistrationForm registrationForm = new RegistrationForm();

        model.addAttribute("registrationForm", registrationForm);

        return "/registrationpage";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registrationAction(Model model, RegistrationForm registrationForm) {

        userService.registerNewUserAccount(registrationForm);

        String infoMessage = "Попробуйте войти";

        return "/login";
    }


}
