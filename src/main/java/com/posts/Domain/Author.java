package com.posts.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.geometry.Pos;

import javax.persistence.*;
import javax.validation.Constraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by mumarm45 on 31/07/2017.
 */
@Entity
public class Author {
    @Id @GeneratedValue
    private Long id;
    @Size(max = 15,min=2)
    @NotNull
    private String firstName;
    @Size(max = 15,min=2)
    private String lastName;
    @Column(unique = true)
    private String email;

    private boolean enable;

    @JsonIgnore
    private String password;

    @ManyToMany(mappedBy = "authors",fetch = FetchType.EAGER)
    private List<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
