package com.ssafy.ssafymate.dto.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(value = "회원 정보 수정", description = "프로필 사진, 자기소개, 희망직무, 기술 스택, Github URL 등 수정할 회원 정보를 담는 클래스")
public class UserModifyRequestDto {

    @ApiModelProperty(value = "프로필 사진")
    private MultipartFile profileImg;

    @ApiModelProperty(value = "자기소개", example = "안녕하세요...")
    private String selfIntroduction;

    @ApiModelProperty(value = "희망 포지션1", example = "프론트엔드 (Front-end)")
    private String job1;

    @ApiModelProperty(value = "희망 포지션2", example = "백엔드 (Back-end)")
    private String job2;

    @ApiModelProperty(value = "기술 스택")
    private String techStacks;

    @ApiModelProperty(value = "Github URL", example = "https://github.com/ssafy-mate")
    private String githubUrl;

    @ApiModelProperty(value = "기타 URL", example = "https://velog.io/@ssafy-mate")
    private String etcUrl;

}
