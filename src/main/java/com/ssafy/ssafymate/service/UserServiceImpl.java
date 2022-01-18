package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.entity.User;
import com.ssafy.ssafymate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Override
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        return user;
    }

    @Override
    public User userSave(User user) {
        return userRepository.save(user);
    }
}
