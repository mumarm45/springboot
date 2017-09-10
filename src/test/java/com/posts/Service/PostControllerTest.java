package com.posts.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.posts.Controller.PostController;
import com.posts.Domain.Author;
import com.posts.Domain.Post;
import com.posts.Domain.Role;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by mumarm45 on 09/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PostControllerTest {

    @MockBean
    private PostService postService;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @InjectMocks
    private PostController postController;

    List<Post> posts = new ArrayList<>();
    Post post;

    @Before
    public void setup() {

        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
        post = new Post();
        post.setId(1L);
        post.setTitle("First");
        post.setBody("First paragraph");
        Role role = new Role();
        role.setName("ROLE_USER");
        List<Role> roles = Arrays.asList(role);

        BCryptPasswordEncoder encryptPassword = new BCryptPasswordEncoder();
        Author author = new Author();
        author.setEmail("mumarm45@gmail.com");
        author.setEnable(true);
        author.setPassword(encryptPassword.encode("password"));
        author.setRoles(roles);

        post.setAuthor(author);

        posts.add(post);

    }


    @Test
    @WithMockUser(roles = "USER")
    public void postList() throws Exception {

        when(postService.list()).thenReturn(posts);

        this.mockMvc
                .perform(get("/post"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("First")));

        verify(postService, times(1)).list();
        verifyNoMoreInteractions(postService);


    }

    @Test
    @WithAnonymousUser
    public void postListAnonymousUser() throws Exception {

        when(postService.list()).thenReturn(posts);

        this.mockMvc
                .perform(get("/post"))
                .andExpect(status().isUnauthorized());


    }

    @Test
    @WithMockUser(roles = "USER")
    public void getOnePost() throws Exception {

        when(postService.getOne(1L)).thenReturn(post);

        this.mockMvc
                .perform(get("/post/{id}", 1))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("First")));

        verify(postService, times(1)).getOne(1L);
        verifyNoMoreInteractions(postService);

    }

    @Test
    @WithMockUser(roles = "USER")
    public void getOnePostNotFound() throws Exception {

        when(postService.getOne(3L)).thenReturn(null);

        this.mockMvc
                .perform(get("/post/{id}", 3))
                .andExpect(status().isNotFound());

        verify(postService, times(1)).getOne(3L);
        verifyNoMoreInteractions(postService);

    }


    @Test
    @WithMockUser(roles = "USER")
    public void createPost() throws Exception {

        Post newpost = new Post();
        newpost.setTitle("Second");
        newpost.setBody("Second paragraph");
        posts.add(newpost);


        when(postService.create(newpost)).thenReturn(newpost);

        this.mockMvc
                .perform(post("/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newpost)))
                .andExpect(status().isCreated());
        //verify(postService, times(1)).create(newpost);
//        verifyNoMoreInteractions(postService);

    }


    @Test
    @WithMockUser(roles = "USER")
    public void updatePost() throws Exception {
        Post newpost = post;
        newpost.setTitle("Second");
        newpost.setBody("Second paragraph");


        when(postService.getOne(1L)).thenReturn(post);
        when(postService.update(newpost)).thenReturn(newpost);

        this.mockMvc
                .perform(put("/post/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newpost)))
                .andExpect(status().is2xxSuccessful());
    }


    @Test
    @WithMockUser(roles = "USER")
    public void updatePostNoFound() throws Exception {
        Post newpost = post;
        newpost.setTitle("Second");
        newpost.setBody("Second paragraph");
        posts.set(0, post);


        when(postService.getOne(1L)).thenReturn(null);


        this.mockMvc
                .perform(put("/post/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newpost)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void deletePostNoFound() throws Exception {


        when(postService.getOne(1L)).thenReturn(null);


        this.mockMvc
                .perform(delete("/post/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }@Test
    @WithMockUser(roles = "USER")
    public void deletePost() throws Exception {


        when(postService.getOne(1L)).thenReturn(post);
       doNothing().when(postService).delete(1L);


        this.mockMvc
                .perform(delete("/post/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}
