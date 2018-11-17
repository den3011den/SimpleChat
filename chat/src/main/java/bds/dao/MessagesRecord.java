package bds.dao;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.AUTO;

// класс отображения записи таблицы БД messages - сообщения чата
@Entity
@Table(name = "messages")
public class MessagesRecord {

    // унакальный id сообщения чата
    @Id
    @GeneratedValue(strategy = AUTO)
    @NotNull
    @Column(name = "ID", nullable = false)
    int id;

    // id пользователя, приславшего сообщение. Связь с таблицей users по полю id
    @NotNull
    @Column(name = "USER_ID", nullable = false)
    int userId;

    // текс сообщения
    @NotNull
    @Column(name = "MESSAGE", nullable = false)
    String message;

    @Override
    public String toString() {
        return "MessagesRecord{ id=" + id + ", userId=" + userId + ", message=" + message + " }";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
