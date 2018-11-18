package bds.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Класс запуска программы
 */
@SpringBootApplication
@ComponentScan({"bds.application", "bds.controllers", "bds.config", "bds.services"})
@PropertySource("classpath:/chat.properties")
public class ChatApplication {

    // для хранения пар значений <login пользователя> - <id последнего переданного сообщения клиенту> (messages.id)
    // используется для того, чтобы знать после какого messages.id записи нужно передавать клиенту, так как они
    // для него новые
    static public final ConcurrentHashMap<String, Integer> topMessageIdForUser = new ConcurrentHashMap<>();

    // main
    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }

}
