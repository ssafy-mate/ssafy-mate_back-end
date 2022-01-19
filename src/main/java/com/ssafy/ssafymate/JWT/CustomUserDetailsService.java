package com.ssafy.ssafymate.JWT;

import com.ssafy.ssafymate.entity.User;
import com.ssafy.ssafymate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

//    @Autowired
//    private CustomUserDetailsImpl userDetails;

    // DB에서 사용자가 있는지 확인
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getUserByEmail(email);
        if(user != null){
            CustomUserDetails userDetails = new CustomUserDetails(user);
            return userDetails;
        }
        return null;
    }
}
