package com.ssafy.ssafymate.dto.response;

import com.ssafy.ssafymate.entity.TechStack;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TechStacksResponseDto {

    @ApiModelProperty(name="기술 스택 리스트", example = "[]")
    List<TechStack> techStackList;

    public static TechStacksResponseDto of (List<TechStack> techStacks){

        TechStacksResponseDto res = new TechStacksResponseDto();
        res.setTechStackList(techStacks);
        return res;

    }

}
