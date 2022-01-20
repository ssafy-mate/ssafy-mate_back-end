package com.ssafy.ssafymate.dto.response;

import com.ssafy.ssafymate.common.BaseResponseBody;
import lombok.Setter;

@Setter
public class BelongToTeam extends BaseResponseBody {
    private Boolean belongToTema;

    public static BelongToTeam of(Integer statusCode, Boolean success, String message, Boolean belongToTema){
        BelongToTeam res = new BelongToTeam();
        res.setStatusCode(statusCode);
        res.setSuccess(success);
        res.setMessage(message);
        res.setBelongToTema(belongToTema);
        return res;
    }
}
