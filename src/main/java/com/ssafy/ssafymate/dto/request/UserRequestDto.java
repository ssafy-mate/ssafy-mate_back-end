package com.ssafy.ssafymate.dto.request;

import com.ssafy.ssafymate.entity.UserStack;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRequestDto {

    private String campus;

    private String ssafyTrack;

    private String studentNumber;

    private String studentName;

    private String email;

    private String password;

    // private MultipartFile profileImg;

    private String selfIntroduction;

    private String job1;

    private String job2;

    List<UserStack> techStacks = new ArrayList<>();

    private String githubUrl;

    private String etcUrl;

    private Boolean agreement;

}
