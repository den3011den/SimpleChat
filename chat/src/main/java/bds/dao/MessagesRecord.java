package bds.dao;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.AUTO;

public class MessagesRecord {

    @Id
    @GeneratedValue(strategy = AUTO)
    @NotNull
    @Column(name = "ID", nullable = false)
    int id;

    @NotNull
    @Column(name = "USER_ID", nullable = false)
    int userId;

    @NotNull
    @Column(name = "MESSAGE", nullable = false)
    String message;

    @Override
    public String toString() {
        return "MessagesRecord{ id=" + id + ", userId=" + userId + ", message=" + message  + " }";
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
