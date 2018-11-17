package bds.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

// класс для создания объектов для обмена данными между клиентом и сервером
// в формате json
public class MessageDTO {

    // логин пользователя
    private String login;
    // содержимое сообщения прользователя
    private String message;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // из объекта MessageDTO в json
    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

    // из json в объект MessageDTO
    public static MessageDTO fromJson(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        MessageDTO messageDTO = mapper.readValue(jsonString, MessageDTO.class);
        return messageDTO;
    }

    // toString будет выводить объект в json
    @Override
    public String toString() {
        try {
            return toJson();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
