package com.example.app.entity;

/**
 * Created by angel on 14.05.2014.
 */
public class Sing_up_entity {

    private String login;
    private String password;

    public final static Sing_up_entity instance = new Sing_up_entity();

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
}
