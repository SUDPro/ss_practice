package com.simbirsoft.models;

import com.simbirsoft.enumTypes.UserAccess;
import com.simbirsoft.enumTypes.UserTypes;

import javax.persistence.*;

@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserAccess access;

    @Enumerated(EnumType.STRING)
    private UserTypes type;

    public User() {
    }

    public User(String login, String password, UserAccess access, UserTypes type) {
        this.login = login;
        this.password = password;
        this.access = access;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public UserAccess getAccess() {
        return access;
    }

    public void setAccess(UserAccess access) {
        this.access = access;
    }

    public UserTypes getType() {
        return type;
    }

    public void setType(UserTypes type) {
        this.type = type;
    }
}
