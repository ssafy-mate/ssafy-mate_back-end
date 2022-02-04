package com.ssafy.ssafymate.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(value = "비밀번호 재설정 - 인증 코드 확인", description = "이메일, 인증 코드를 담는 클래스")
public class PwSearchRequestDto {

    @ApiModelProperty(value = "이메일", example = "ssafymate@gmail.com")
    private String userEmail;

    @ApiModelProperty(value = "인증코드", example = "Axzdkj11")
    private String code;
}
