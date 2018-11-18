package bds.dao;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.AUTO;

// класс отображения записи таблицы БД userrole - список ролей привязанных к пользователям системы
@Entity
@Table(name = "userrole")
public class UserRoleRecord {

    // уникальный id записей таблицы
    @Id
    @GeneratedValue(strategy = AUTO)
    @NotNull
    @Column(name = "ID", nullable = false)
    int id;

    // id пользователя. Связь с таблицей users по полю users.id
    @NotNull
    @Column(name = "USER_ID", nullable = false)
    private
    int userId;

    // id роли. Связь с таблицей roles по полю roles.id
    @NotNull
    @Column(name = "ROLE_ID", nullable = false)
    private
    int roleId;

    @Override
    public String toString() {
        return "UserRoleRecord{ id=" + id + ", userId=" + userId + ", roleId=" + roleId + " }";
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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
