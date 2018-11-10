package bds.controllers;

import bds.dao.MessagesRecord;
import bds.dao.UsersRecord;
import bds.dao.repo.MessagesRepository;
import bds.dao.repo.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@PropertySource("classpath:/chat.properties")
@EnableJpaRepositories("bds.dao")
@EntityScan(basePackageClasses = {bds.dao.MessagesRecord.class, bds.dao.UsersRecord.class})
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    static private List<UsersRecord> allUsers;
    static private List<MessagesRecord> allMessages;

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

    public void deleteUsersRecord(UsersRecord usersRecord) {
        usersRepository.delete(usersRecord);
    }

    public void updateUsersRecord(UsersRecord userRecord) {
        usersRepository.save(userRecord);
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

}
