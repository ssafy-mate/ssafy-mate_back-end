package com.ssafy.ssafymate.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BelongToTeam  {
    private Boolean belongToTeam;

    public static BelongToTeam of(Boolean belongToTeam){
        BelongToTeam res = new BelongToTeam();
        res.setBelongToTeam(belongToTeam);
        return res;
    }
}
