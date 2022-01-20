package com.ssafy.ssafymate.dto.request;

import com.ssafy.ssafymate.entity.UserStack;
import lombok.*;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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

    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#$%^&*()+|=]{6,20}$", message = "비밀번호는 영어와 숫자를 포함해서 6 ~ 12자리 이내로 입력해주세요.")
    private String password;

    // private MultipartFile profileImg;

    private String selfIntroduction;

    private String job1;

    private String job2;

    @Size(min=2)
    List<UserStack> techStacks = new ArrayList<>();

    private String githubUrl;

    private String etcUrl;

    private Boolean agreement;

}
