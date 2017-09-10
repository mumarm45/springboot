package com.posts.ExceptionHandler;

import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by mumarm45 on 03/08/2017.
 */
@ControllerAdvice
public class CommonErrorAdvice {

    @ResponseBody
    @ExceptionHandler(PostNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    VndErrors postNotFound(PostNotFound pst){
        return new VndErrors("error",pst.getMessage()) ;
    }

//    VndErrors UserNotFound(String msg){
//
//    }

}
