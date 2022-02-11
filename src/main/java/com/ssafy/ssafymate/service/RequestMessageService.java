package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.entity.RequestMessage;
import com.ssafy.ssafymate.entity.Team;
import com.ssafy.ssafymate.entity.User;

import java.util.List;

public interface RequestMessageService {

    RequestMessage userRequest(User sender, Team team, String message);

    RequestMessage teamRequest(User sender, Long receiverId, Team team, String message);

    List<RequestMessage> receiveList(User user, String project);

    List<RequestMessage> sendList(User user, String project);

    RequestMessage getRequest(Long requestId);

    Integer updateReadCheckRejection(Long id, String readCheck);

    Integer updateReadCheckApproval(Long id, String readCheck,Long userId, Team teamId);

}
