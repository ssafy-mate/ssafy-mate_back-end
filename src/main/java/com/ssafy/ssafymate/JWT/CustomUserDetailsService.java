package com.ssafy.ssafymate.JWT;

import com.ssafy.ssafymate.entity.User;
import com.ssafy.ssafymate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("customUserDetailService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    // DB에서 사용자가 있는지 확인
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElse(null);
        if(user != null){
            CustomUserDetails userDetails = new CustomUserDetails(user);
            return userDetails;
        }
        return null;
    }

}
