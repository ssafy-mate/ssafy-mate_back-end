package com.ssafy.ssafymate.dto.UserDto;

import com.ssafy.ssafymate.entity.User;
import com.ssafy.ssafymate.entity.UserStack;
import com.ssafy.ssafymate.entity.UserTeam;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
public class UserDetailDto {

    private Long userId;
    private String userName;
    private String userEmail;

    private String profileImgUrl;
    private String campus;
    private String ssafyTrack;
    private String selfIntroduction;

    private String job1;
    private String job2;

    List<UserProjectDto> projects = new ArrayList<>();
    List<UserStack> techStacks = new ArrayList<>();

    private String githubUrl;

    private String etcUrl;

    public static UserDetailDto of(User user){
        UserDetailDto res = new UserDetailDto();
        res.setUserId(user.getId());
        res.setUserName(user.getStudentName());
        res.setUserEmail(user.getEmail());
        res.setProfileImgUrl(user.getProfileImg());
        res.setCampus(user.getCampus());
        res.setSsafyTrack(user.getSsafyTrack());
        res.setSelfIntroduction(user.getSelfIntroduction());
        res.setJob1(user.getJob1());
        res.setJob2(user.getJob2());
        res.setGithubUrl(user.getGithubUrl());
        res.setEtcUrl(user.getEtcUrl());
        res.setProjects(UserProjectDto.of(user.getTeams(),user));
        res.setTechStacks(user.getTechStacks());

        return res;
    }

}
