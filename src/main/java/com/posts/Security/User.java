package com.posts.Security;

import com.posts.Domain.Author;
import com.posts.Domain.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by mumarm45 on 04/08/2017.
 */
public class User implements UserDetails {

    private Author author;

   public User(Author author) {
        this.author  = author;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return mapToGrantedAuthorities(author.getRoles());
    }

    @Override
    public String getPassword() {
        return this.author.getPassword();
    }

    @Override
    public String getUsername() {
        return this.author.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.author.isEnable();
    }

    private List<GrantedAuthority> mapToGrantedAuthorities(List<Role> roles){
        roles.stream().map(Role::getName).forEach(System.out::print);

       return roles.stream()
                .map(role-> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
