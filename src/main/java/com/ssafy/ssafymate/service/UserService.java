package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.entity.User;

public interface UserService {
    User getUserByEmail(String email);
    User userSave(User user);
}
