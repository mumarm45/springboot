package com.posts.src;

/**
 * Created by mumarm45 on 05/08/2017.
 */
public class UserRequest {
    private String username;
    private String password;

    public UserRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public  UserRequest(){}

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
