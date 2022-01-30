package com.ssafy.ssafymate.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(value = "비밀번호 재설정 입력 정보", description = "이메일, 학번, 이름, 비밀번호를 담는 클래스")
public class PwModifyRequestDto {

    @ApiModelProperty(value = "이메일", example = "gildong@gmail.com")
    private String email;

    @ApiModelProperty(value = "학번", example = "0000000")
    private String studentNumber;

    @ApiModelProperty(value = "이름", example = "홍길동")
    private String studentName;

    @ApiModelProperty(value = "비밀번호", example = "a12345")
    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#$%^&*()+|=]{6,20}$", message = "비밀번호는 영어와 숫자를 포함해서 6 ~ 12자리 이내로 입력해주세요.")
    private String password;
}
