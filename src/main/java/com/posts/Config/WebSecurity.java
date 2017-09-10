package com.posts.Config;


import com.posts.Security.AuthenticationEntryPointImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.posts.src.JwtFilterToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by mumarm45 on 04/08/2017.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passWordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Autowired
    private AuthenticationEntryPointImp unauthorizedHandler;

    @Autowired
    protected void configureAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(passWordEncoder());
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
     public JwtFilterToken authenticationTokenFilterBean(){
        return new JwtFilterToken();
     }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js").permitAll()
                .antMatchers("/auth/**").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        http.headers().cacheControl();

       // super.configure(http);

    }
}
