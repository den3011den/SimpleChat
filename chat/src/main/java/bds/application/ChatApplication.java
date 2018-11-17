package bds.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

 import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
@ComponentScan({"bds.application","bds.controllers", "bds.config","bds.services"})
@PropertySource("classpath:/chat.properties")
public class ChatApplication {

    static public ConcurrentHashMap<String, Integer> topMessageIdForUser = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }

}
