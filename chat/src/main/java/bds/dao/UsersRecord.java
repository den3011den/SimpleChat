package bds.dao;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.AUTO;

// класс отображения записи таблицы БД users - справочник пользователей системы системы
@Entity
@Table(name = "users")
public class UsersRecord {

    // уникальный id пользователя в системе
    @Id
    @GeneratedValue(strategy = AUTO)
    @NotNull
    @Column(name = "ID", nullable = false)
    int id;

    // логин пользователя в системе
    @NotNull
    @Column(name = "LOGIN", nullable = false)
    String login;

    // пароль пользователя в системе в зашифрованом виде
    @NotNull
    @Column(name = "PASSWORD", nullable = false)
    String password;

    public UsersRecord() {
    }

    ;

    public UsersRecord(String login) {
        this.login = login;
    }

    ;

    @Override
    public String toString() {
        return "UsersRecord{ id=" + id + ", login=" + login + ", password=" + password + " }";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // идентичными считаем пользователей с одинаковым логином
    @Override
    public boolean equals(Object o) {
        UsersRecord another = (UsersRecord) o;
        return (login.compareTo(another.login) == 0);
    }


}
