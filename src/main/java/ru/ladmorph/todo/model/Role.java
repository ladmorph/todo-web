package ru.ladmorph.todo.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@Table(name = "roles")
public class Role extends BaseEntity {

    @Column(name = "role_name", unique = true)
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
