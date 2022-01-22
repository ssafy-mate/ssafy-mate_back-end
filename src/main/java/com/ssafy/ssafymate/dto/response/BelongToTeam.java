package com.ssafy.ssafymate.dto.response;

import com.ssafy.ssafymate.common.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BelongToTeam extends BaseResponseBody {
    private Boolean belongToTeam;

    public static BelongToTeam of(Integer statusCode, Boolean success, String message, Boolean belongToTeam){
        BelongToTeam res = new BelongToTeam();
        res.setStatusCode(statusCode);
        res.setSuccess(success);
        res.setMessage(message);
        res.setBelongToTeam(belongToTeam);
        return res;
    }
}
