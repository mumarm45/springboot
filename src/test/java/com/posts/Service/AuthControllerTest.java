package com.posts.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.posts.Domain.Author;
import com.posts.Domain.Role;
import com.posts.Security.User;
import com.posts.src.UserRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by mumarm45 on 09/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthControllerTest {


    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }


    @Test
    @WithAnonymousUser
    public void successfulAuthenticationWithAnonymousUser() throws Exception
    {
        UserRequest userRequest = new UserRequest("user","password");


        this.mockMvc.perform(post("/auth")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(userRequest)))
                .andExpect(status().is2xxSuccessful());


    }


    @Test
    @WithMockUser(roles = "USER")
    public  void refreshTokenWithUserRole() throws Exception {

     Role role = new Role();
     role.setId(0L);
     role.setName("ROLE_USER");
        List<Role> roles = Arrays.asList(role);

        BCryptPasswordEncoder encryptPassword = new BCryptPasswordEncoder();
        Author author = new Author();
        author.setEmail("mumarm45@gmail.com");
        author.setEnable(true);
        author.setPassword(encryptPassword.encode("password"));
        author.setRoles(roles);


        User user  = new User(author);


       when( tokenService.getUsernameFromToken(any())).thenReturn(user.getUsername());
       when(userDetailsService.loadUserByUsername(eq(user.getUsername()))).thenReturn(user);



       this.mockMvc
               .perform(get("/refresh"))
               .andExpect(status().is2xxSuccessful());





    }

    @Test
    @WithAnonymousUser
    public  void  refreshTokenWithAnonymousUser() throws Exception {
           this.mockMvc
                   .perform(get("/refresh"))
                   .andExpect(status().isUnauthorized());
    }


}
