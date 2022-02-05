package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.dto.UserDto.UserBoardInterface;
import com.ssafy.ssafymate.dto.UserDto.UserBoardDto;
import com.ssafy.ssafymate.dto.request.*;
import com.ssafy.ssafymate.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    // 유저 조회 - ID로 찾기
    User getUserById(Long id);

    // 유저 조회 - Email로 찾기
    User getUserByEmail(String email);

    // 유저 조회 - 학번, 이름으로 찾기
    User getEmailByStudentNumberAndStudentName(String studentNumber, String studentName);

    // 유저 비밀번호 수정
    User passwordModify(PwModifyRequestDto pwModifyRequestDto, User user);

    // 유저 생성
    User userSave(UserRequestDto userRequestDto, MultipartFile multipartFile) throws IOException;

    // 유저 수정
    User userModify(UserModifyRequestDto userModifyRequestDto, MultipartFile multipartFile, User user) throws IOException;

    // 유저 리스트 조회
    Page<UserBoardInterface> userList(Pageable pageable, UserListRequestDto user);

    // 유저 리스트 변환
    List<UserBoardDto> userBoarConvert(List<UserBoardInterface> users, String project);

    String selectProjectTrack(User user, UserSelectProjectTrackRequsetDto userSelectProjectTrackRequsetDto);
}
