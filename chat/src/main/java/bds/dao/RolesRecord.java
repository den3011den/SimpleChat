package bds.dao;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.AUTO;

// класс отображения записи таблицы БД roles - справочник ролей системы
@Entity
@Table(name = "roles")
public class RolesRecord {

    // уникальный id роли
    @Id
    @GeneratedValue(strategy = AUTO)
    @NotNull
    @Column(name = "ID", nullable = false)
    int id;

    // наименование роли
    @NotNull
    @Column(name = "name", nullable = false)
    private
    String name;

    @Override
    public String toString() {
        return "RolesRecord{ id=" + id + ", name=" + name + " }";
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
