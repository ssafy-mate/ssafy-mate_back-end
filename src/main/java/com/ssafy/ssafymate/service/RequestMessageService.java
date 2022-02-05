package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.entity.RequestMessage;
import com.ssafy.ssafymate.entity.Team;
import com.ssafy.ssafymate.entity.User;

import java.util.List;

public interface RequestMessageService {
    RequestMessage userRequest(User sender, Team team, String message);

    RequestMessage teamRequest(User sender, Long receiverId, Team team, String message);

    List<RequestMessage> messageList(User user);
}
