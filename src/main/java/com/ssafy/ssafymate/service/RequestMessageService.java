package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.entity.RequestMessage;
import com.ssafy.ssafymate.entity.Team;
import com.ssafy.ssafymate.entity.User;

import java.util.List;

public interface RequestMessageService {

    // 팀 지원 요청 (사용자 -> 팀)
    RequestMessage userRequest(User sender, Team team, String message);

    // 팀 합류 요청 (팀 (팀장) -> 사용자)
    RequestMessage teamRequest(User sender, Long receiverId, Team team, String message);

    // 받은 요청 리스트
    List<RequestMessage> receiveList(User user, String project);

    // 보낸 요청 리스트
    List<RequestMessage> sendList(User user, String project);

    // 아이디로 요청 검색
    RequestMessage getRequest(Long requestId);

    // 받은 요청 응답 (거절/취소)
    Integer updateReadCheckRejection(Long id, String readCheck);

    // 받은 요청 수락
    Integer updateReadCheckApproval(Long id, String readCheck,Long userId, Team teamId);

    // 과거에 같은 요청 보냈는지 확인
    RequestMessage findSameRequest(Long senderId, Long teamId, Long receiverId);

}
