package com.posts.Controller;

import com.posts.ExceptionHandler.PostNotFound;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by mumarm45 on 31/07/2017.
 */
@ResponseBody

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PostControllerAdvice {

    VndErrors userNotFoundExceptionHandler(PostNotFound ex) {
        return new VndErrors("error", ex.getMessage());
    }
}
