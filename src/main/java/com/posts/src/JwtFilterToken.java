package com.posts.src;

import com.posts.Service.TimeService;
import com.posts.Service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by mumarm45 on 07/08/2017.
 */
public class JwtFilterToken extends OncePerRequestFilter {


    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserDetailsService userDetailsService;



    @Value("${auth.header}")
    String authToken;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String headerToken = httpServletRequest.getHeader(authToken);

        String username =  tokenService.getUsernameFromToken(headerToken);

        if(username !=null && SecurityContextHolder.getContext().getAuthentication()==null){

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if(tokenService.validateToken(headerToken,userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,null,userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }


        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);



    }
}
