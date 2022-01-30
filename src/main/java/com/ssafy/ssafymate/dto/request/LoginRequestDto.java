package com.ssafy.ssafymate.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "로그인 입력 정보", description = "이메일, 비밀번호를 입력하는 클래스")
public class LoginRequestDto {

    @ApiModelProperty(value = "이메일", example = "gildong@gmail.com")
    private String userEmail;

    @ApiModelProperty(value = "비밀번호", example = "a123123a")
    private String password;
}
