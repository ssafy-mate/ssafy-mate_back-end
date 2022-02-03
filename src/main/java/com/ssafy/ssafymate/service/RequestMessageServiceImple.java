package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.entity.RequestMessage;
import com.ssafy.ssafymate.entity.Team;
import com.ssafy.ssafymate.entity.User;
import com.ssafy.ssafymate.repository.RequestMessageRepository;
import com.ssafy.ssafymate.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("requestMessageService")
public class RequestMessageServiceImple implements RequestMessageService{

    @Autowired
    RequestMessageRepository requestMessageRepository;

    @Autowired
    TeamRepository teamRepository;

    @Override
    public RequestMessage userRequest(User sender, Team team, String message) {
        RequestMessage requestMessage = RequestMessage.builder()
                .senderId(sender.getId())
                .teamId(team.getId())
                .receiverId(team.getOwner().getId())
                .message(message)
                .type("userRequest")
                .build();
        return requestMessageRepository.save(requestMessage);
    }

    @Override
    public RequestMessage teamRequest(User sender, Long receiverId, Team team, String message) {
        RequestMessage requestMessage = RequestMessage.builder()
                .senderId(sender.getId())
                .teamId(team.getId())
                .receiverId(receiverId)
                .message(message)
                .type("teamRequest")
                .build();
        return requestMessageRepository.save(requestMessage);
    }

    @Override
    public List<RequestMessage> messageList(User user) {
        return null;
    }
}
