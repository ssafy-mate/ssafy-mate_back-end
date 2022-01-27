package com.ssafy.ssafymate.JWT;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.ssafymate.entity.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class CustomUserDetails implements UserDetails {

    @Autowired
    private User user;

    boolean accountNonExpired = true;
    boolean accountNonLocked = true;
    boolean credentialNonExpired = true;
    boolean enabled = true;

    private Collection<? extends GrantedAuthority> authorities;
    // List<GrantedAuthority> roles = new ArrayList<>();

    public CustomUserDetails (User user){
        super();
        this.user=user;

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));

//        return new CustomUserDetails(
//                user.getId(),
//                user.getEmail(),
//                user.getEmail(),
//                user.getPassword(),
//                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoleList().forEach(r-> {
            authorities.add(() -> r);
        });
        return authorities;
    }

//    public void setAuthorities(List<GrantedAuthority> roles) {
//        this.roles = roles;
//    }

    public User getUser(){
        return this.user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
