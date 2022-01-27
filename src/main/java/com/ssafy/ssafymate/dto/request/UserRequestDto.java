package com.ssafy.ssafymate.dto.request;

import com.ssafy.ssafymate.entity.UserStack;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "회원가입 입력 정보", description = "캠퍼스, 이메일, 학번, 이름, 비밀번호, 자기소개 등 회원 정보를 담는 클래스")
public class UserRequestDto {

    @ApiModelProperty(value = "캠퍼스", example = "서울")
    private String campus;

    @ApiModelProperty(value = "트랙", example = "Java Track")
    private String ssafyTrack;

    @ApiModelProperty(value = "학번", example = "0000000")
    private String studentNumber;

    @ApiModelProperty(value = "이름", example = "홍길동")
    private String userName;

    @ApiModelProperty(value = "이메일", example = "gildong@gmail.com")
    private String userEmail;

    @ApiModelProperty(value = "비밀번호", example = "a12345")
    @Pattern(regexp="^(?[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#$%^&*()+|=]{6,255=.*}$", message = "비밀번호는 영어와 숫자를 포함해서 6자리 이상 입력해주세요.")
    private String password;

    @ApiModelProperty(value = "자기소개", example = "안녕하세요...")
    private String selfIntroduction;

    @ApiModelProperty(value = "희망 포지션1", example = "프론트엔드 (Front-end)")
    private String job1;

    @ApiModelProperty(value = "희망 포지션2", example = "백엔드 (Back-end)")
    private String job2;

//    @ApiModelProperty(value = "기술스택", example = [{\"techStackLevel\": \"상\", \"techStackName\": \"Java\"}, {\"techStackLevel\": \"중\", \"techStackName\": \"Python\"}])
    @Size(min=2)
    List<UserStack> techStacks = new ArrayList<>();

    @ApiModelProperty(value = "Github URL", example = "https://github.com/ssafy-mate")
    private String githubUrl;

    @ApiModelProperty(value = "기타 URL", example = "https://velog.io/@ssafy-mate")
    private String etcUrl;

    @ApiModelProperty(value = "개인정보 동의여부", example = "true")
    private String agreement;

}
