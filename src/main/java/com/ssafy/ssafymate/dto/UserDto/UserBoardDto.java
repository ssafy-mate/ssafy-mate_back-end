package com.ssafy.ssafymate.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserBoardDto {
    Long userId;
    String profileImg;
    String campus;
    String projectTrack;
    String ssafyTrack;
    String userName;
    List<UserListStackDto> techStacks;
    String job1;
    String job2;
    String githubUrl;
    Boolean belongToTeam;


}
