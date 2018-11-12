package bds.dao;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name="roles")
public class RolesRecord {

    @Id
    @GeneratedValue(strategy = AUTO)
    @NotNull
    @Column(name = "ID", nullable = false)
    int id;

    @NotNull
    @Column(name = "name", nullable = false)
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
