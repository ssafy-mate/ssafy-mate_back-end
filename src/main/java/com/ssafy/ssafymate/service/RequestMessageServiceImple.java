package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.entity.RequestMessage;
import com.ssafy.ssafymate.entity.Team;
import com.ssafy.ssafymate.entity.User;
import com.ssafy.ssafymate.entity.UserTeam;
import com.ssafy.ssafymate.repository.RequestMessageRepository;
import com.ssafy.ssafymate.repository.TeamRepository;
import com.ssafy.ssafymate.repository.UserTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("requestMessageService")
public class RequestMessageServiceImple implements RequestMessageService {

    @Autowired
    private RequestMessageRepository requestMessageRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserTeamRepository userTeamRepository;

    @Override
    public RequestMessage userRequest(User sender, Team team, String message) {

        RequestMessage requestMessage = RequestMessage.builder()
                .senderId(sender.getId())
                .teamId(team.getId())
                .receiverId(team.getOwner().getId())
                .message(message)
                .project(team.getProject())
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
                .project(team.getProject())
                .type("teamRequest")
                .build();
        return requestMessageRepository.save(requestMessage);

    }

    @Override
    public List<RequestMessage> receiveList(User user, String project) {

        return requestMessageRepository.findAllByReceiverIdAndProjectOrderByCreatedDateTimeDesc(user.getId(), project);

    }

    @Override
    public List<RequestMessage> sendList(User user, String project) {

        return requestMessageRepository.findAllBySenderIdAndProjectOrderByCreatedDateTimeDesc(user.getId(), project);

    }

    @Override
    public RequestMessage getRequest(Long requestId) {
        return requestMessageRepository.findById(requestId);
    }

    @Transactional
    @Modifying
    @Override
    public Integer updateReadCheckRejection(Long id, String readCheck) {

        return requestMessageRepository.updateRead(id, readCheck);

    }

    @Transactional
    @Modifying
    @Override
    public Integer updateReadCheckApproval(Long id, String readCheck, Long userId, Team team) {

        Integer answer = requestMessageRepository.updateRead(id, readCheck);
        if (answer == 1) {
            UserTeam userTeam = UserTeam.builder()
                    .userId(userId)
                    .teamId(team.getId())
                    .build();
            userTeamRepository.save(userTeam);
            requestMessageRepository.updateReadExpirationUser(userId, team.getProject());
        }
        if (teamRepository.isRecruit(team.getId()).equals("false")) {
            requestMessageRepository.updateReadExpirationTeam(team.getId(), team.getProject());
        }
        return answer;

    }

    @Override
    public RequestMessage findSameRequest(Long senderId, Long teamId, Long receiverId) {

        return requestMessageRepository.findBySenderIdAndTeamIdAndReceiverIdAndRequestStatus(senderId, teamId, receiverId, "pending").orElse(null);

    }

    @Transactional
    @Modifying
    @Override
    public Integer updateReadCheck(Long requestId, Long userId) {
        RequestMessage requestMessage = requestMessageRepository.findById(requestId);
        if(requestMessage.getSenderId().equals(userId)){
            return requestMessageRepository.updateSenderRead(requestId);
        }
        else if(requestMessage.getReceiverId().equals(userId)){
            return requestMessageRepository.updateReceiverRead(requestId);
        }
        return null;

    }

}
