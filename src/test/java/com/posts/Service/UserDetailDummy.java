package com.posts.Service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;

/**
 * Created by mumarm45 on 09/08/2017.
 */
public class UserDetailDummy implements UserDetails {

    private String username;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    UserDetailDummy(String username){
      this.username = username;
    }
    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
