package com.posts.Security;

import com.posts.Domain.Author;
import com.posts.Repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by mumarm45 on 04/08/2017.
 */
@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private AuthorRepository authorRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Author author =authorRepository.findFirstByEmail(username);
        return new User(author);
    }
}
