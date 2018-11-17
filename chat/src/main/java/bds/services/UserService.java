package bds.services;

import bds.dao.MessagesRecord;
import bds.dto.MessageDTO;
import bds.model.RegistrationForm;
import bds.dao.UserRoleRecord;
import bds.dao.UsersRecord;
import bds.dao.repo.UserRoleRepository;
import bds.dao.repo.UsersRepository;
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import java.util.*;
import java.sql.*;

import static bds.application.ChatApplication.topMessageIdForUser;

@Service
public class UserService implements IUserService {

    @Value("${chat.maxMessagesOnThePage.prop}")
    private String maxMessagesOnThePage;

    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    static private List<UsersRecord> allUsers;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;


    @Bean
    public SessionFactory sessionFactory(EntityManagerFactory emf) {
        return emf.unwrap(SessionFactory.class);
    }


    @Override
    public UsersRecord registerNewUserAccount(RegistrationForm registrationForm) throws LoginExistsException {
        if (loginExists(registrationForm.getUserName())) {
            throw new LoginExistsException("Уже есть логин: " + registrationForm.getUserName());
        }

        UsersRecord usersRecord = new UsersRecord();

        usersRecord.setLogin(registrationForm.getUserName());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        usersRecord.setPassword(passwordEncoder.encode(registrationForm.getPassword()));

        UsersRecord savedRecord = usersRepository.save(usersRecord);
        UserRoleRecord userRoleRecord = new UserRoleRecord();
        userRoleRecord.setUserId(savedRecord.getId());
        userRoleRecord.setRoleId(1);

        userRoleRepository.save(userRoleRecord);

        return savedRecord;
    }

    public boolean loginExists(String login) {

        allUsers = (List<UsersRecord>) usersRepository.findAll();

        if (allUsers.size() == 0) {
            return false;
        }

        boolean loginContains = allUsers.contains(new UsersRecord(login));
        return loginContains;
    }


    public void forgetTopMessageIdForUser(String login) {

        if (!login.isEmpty()) {
            UserService.LOG.info("forgot top message id for " + login);
            topMessageIdForUser.remove(login);
        }
    }


    public int rememberTopMessageIdForUser(String login) {

        Integer topId = new Integer(0);

        Session session = sessionFactory.openSession();
        Criteria userCriteria = session.createCriteria(UsersRecord.class);
        userCriteria.add(Restrictions.eq("login", login));
        UsersRecord user = (UsersRecord) userCriteria.uniqueResult();
        session.close();

        if (user == null) {
            UserService.LOG.info("Cant find user login: " + login + " in Users");
            return -1;
        }

        int foundId = 0;
        foundId = user.getId();

        if (foundId == 0) {
            UserService.LOG.info("Cant find user login: " + login + " in Users");
            return -1;
        }

        session = sessionFactory.openSession();
        Criteria messageCriteria = session.createCriteria(MessagesRecord.class);
        messageCriteria.addOrder(Order.desc("id"));
        messageCriteria.setMaxResults(1);
        messageCriteria.setFirstResult(0);
        MessagesRecord messagesRecord = (MessagesRecord) messageCriteria.uniqueResult();
        session.close();


        if (messagesRecord == null) {
            UserService.LOG.info("Messages not found. Max message id for " + login + " is 0");
            return 0;
        }

        topId = (Integer) messagesRecord.getId();

        if (topId.intValue() > 0) {
            topMessageIdForUser.put(login, topId);
            UserService.LOG.info("Top message id for " + login + " is " + topId.intValue());
            return topId.intValue();
        }
        return 0;
    }


    public List<MessageDTO> getLatestMessagesForUser(String login) throws ClassNotFoundException, SQLException {

        List<MessageDTO> listForReturn = new ArrayList<>();

        Class.forName("org.h2.Driver"); // подключение драйвера
        Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost:9092/~/ChatDatabase;USER=sa;PASSWORD=12345");

        Statement statement = connection.createStatement();
        // блоки не больше, чем 20 новых сообщений за раз
        String sql = "SELECT MESSAGES.ID AS ID, users.login AS login, messages.message AS message" +
                " FROM MESSAGES, USERS where MESSAGES.ID > " + String.valueOf(topMessageIdForUser.get(login)) +
                " AND MESSAGES.user_id = users.ID" +
                " order by MESSAGES.ID limit " + maxMessagesOnThePage;

        ResultSet rs = statement.executeQuery(sql);
        int records = 0;

        int topId = 0;

        while (rs.next()) {
            records++;
            //извлечение по имени столбца
            int messageId = rs.getInt("id");
            String userLogin = rs.getString("login");
            String message = rs.getString("message");

            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setLogin(userLogin);
            messageDTO.setMessage(message);

            listForReturn.add(messageDTO);

            topId = messageId;

        }

        if (records > 0) {
            topMessageIdForUser.replace(login, topId);
        }

        return listForReturn;
    }


}
