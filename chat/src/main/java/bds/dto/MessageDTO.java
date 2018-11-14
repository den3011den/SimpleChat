package bds.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class MessageDTO {
    private String login;
    private String message;

   /* public MessageDTO() {}

    public MessageDTO(String login, String message) {
        this.login = login;
        this.message = message;
    }*/

    /*public MessageDTO(String jsonString) throws IOException {
        MessageDTO messageDTO = MessageDTO.fromJson(jsonString);
        this.login = messageDTO.getLogin();
        this.login = messageDTO.getMessage();
     }*/

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
