package bds.dao;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "userrole")
public class UserRoleRecord {

    @Id
    @GeneratedValue(strategy = AUTO)
    @NotNull
    @Column(name = "ID", nullable = false)
    int id;

    @NotNull
    @Column(name = "USER_ID", nullable = false)
    int userId;

    @NotNull
    @Column(name = "ROLE_ID", nullable = false)
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
