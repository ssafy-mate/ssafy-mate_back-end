package com.ssafy.ssafymate.dto.ProDto;

import com.ssafy.ssafymate.entity.User;
import com.ssafy.ssafymate.entity.UserTeam;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class ProMemberDto {

    private String studentNumber;
    private String studentName;


    public static List<ProMemberDto> of(List<UserTeam> userTeams, String ownerStudentNumber){
        List<ProMemberDto> res = new ArrayList<>();

        for(UserTeam userTeam : userTeams){
            ProMemberDto proMemberDto = new ProMemberDto();
            User user = userTeam.getUser();
            proMemberDto.setStudentNumber(user.getStudentNumber());
            proMemberDto.setStudentName(user.getStudentName());

            if(!Objects.equals(proMemberDto.getStudentNumber(), ownerStudentNumber)){
                res.add(proMemberDto);
            }
        }

        return res;

    }
}
