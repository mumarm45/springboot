package com.posts.Security;

import org.apache.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by mumarm45 on 04/08/2017.
 */
@Component
public class AuthenticationEntryPointImp implements AuthenticationEntryPoint,Serializable {

    private static final long serialVersionUID = -53635653645L;


    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
     httpServletResponse.sendError(HttpStatus.SC_UNAUTHORIZED,"Un Authorized User,Please pass token in header");
    }
}
