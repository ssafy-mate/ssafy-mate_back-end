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
@ApiModel(value = "이메일 인증 정보", description = "이메일, 인증코드가 담겨있는 클래스")
public class EmailRequestDto {

    @ApiModelProperty(value = "이메일", example = "gildong@gmail.com")
    private String userEmail;

    @ApiModelProperty(value = "인증코드", example = "Mx1kjsxx")
    private String code;

}
