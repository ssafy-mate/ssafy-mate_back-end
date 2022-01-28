package com.ssafy.ssafymate.dto.response;

import com.ssafy.ssafymate.entity.TechStack;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TechStacksResponseDto {

    List<TechStack> tecjStackList;

    public static TechStacksResponseDto of (List<TechStack> techStacks){
        TechStacksResponseDto res = new TechStacksResponseDto();
        res.setTecjStackList(techStacks);
        return res;
    }
}
