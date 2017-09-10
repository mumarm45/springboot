package com.posts.Controller;

import com.posts.Domain.Author;
import com.posts.Repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by mumarm45 on 06/08/2017.
 */
@Controller
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    @RequestMapping(value = "/author/save",method = RequestMethod.POST)
    public ResponseEntity<?> saveAuthor(@RequestBody Author author){

        BCryptPasswordEncoder encryptPassword = new BCryptPasswordEncoder();

        author.setPassword(  encryptPassword.encode(author.getPassword())  );

        authorRepository.save(author);
        return ResponseEntity.ok(author);
    }







}
