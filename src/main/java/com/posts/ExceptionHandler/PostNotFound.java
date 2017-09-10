package com.posts.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by mumarm45 on 31/07/2017.
 */

public class PostNotFound extends RuntimeException {

    public PostNotFound(String msg){

        super(msg);
    }

}
