package com.posts.Controller;

import com.posts.Domain.Post;
import com.posts.ExceptionHandler.PostNotFound;
import com.posts.Service.PostService;
import org.apache.http.HttpStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 *
 * Created by mumarm45 on 31/07/2017.
 */
@RestController
@RequestMapping("/post")
public class PostController {

    private PostService postService;

    @Autowired
    public PostController(PostService postService){
        this.postService = postService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Post> list(){
       return postService.list();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Post create(@RequestBody Post post, HttpServletResponse response){
          response.setStatus(HttpStatus.SC_CREATED);
        return  postService.create(post);
    }
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Post get(@PathVariable Long id){
        Post post =  postService.getOne(id);
        if(post==null){
            throw new PostNotFound("Post not found this "+ id);
        }
         return  post;
    }
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Post update(@RequestBody Post updatePost, HttpServletResponse response,@PathVariable Long id){

        Post foundPost =  postService.getOne(id);
        if(foundPost ==null){
            throw new PostNotFound("Post not found this "+ id);
        }
           foundPost = postService.update(updatePost);

        return  foundPost;
    }@RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Long id){

        Post foundPost =  postService.getOne(id);
        if(foundPost ==null){
            throw new PostNotFound("Post not found this "+ id);
        }
            postService.delete(id);

        return new ResponseEntity<>(org.springframework.http.HttpStatus.NO_CONTENT);
    }

}
