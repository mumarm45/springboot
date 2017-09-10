package com.posts.src;

import java.io.Serializable;

/**
 * Created by mumarm45 on 06/08/2017.
 */
public class UserResponse implements Serializable {



    private String token;

    public UserResponse(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
