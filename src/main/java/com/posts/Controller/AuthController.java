package com.posts.Controller;

import com.posts.Security.User;
import com.posts.Service.TokenService;
import com.posts.src.UserRequest;
import com.posts.src.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by mumarm45 on 05/08/2017.
 */
@RestController

public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private TokenService generateToken;

    @Value("${auth.header}")
    private String header;

    @RequestMapping(value = "${auth.route.authentication.path}",method = RequestMethod.POST)
    public ResponseEntity<?> createAuthToken(@RequestBody UserRequest userRequest) throws AuthenticationException{
       Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userRequest.getUsername(),userRequest.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(userRequest.getUsername());
        final String token =generateToken.createToken(userDetails);

        return ResponseEntity.ok(new UserResponse(token));
    }


    @RequestMapping(value = "/refresh",method = RequestMethod.GET)
    public ResponseEntity<?> refreshToken(HttpServletRequest request) throws AuthenticationException{

        String token =  request.getHeader(header);
        String username = generateToken.getUsernameFromToken(token);
        User user = (User) userDetailsService.loadUserByUsername(username);

        String refreshToken = generateToken.refreshToken(token,user);



        return ResponseEntity.ok(new UserResponse(refreshToken));
    }




}
