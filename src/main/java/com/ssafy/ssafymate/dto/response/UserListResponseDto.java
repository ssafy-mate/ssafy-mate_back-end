package com.ssafy.ssafymate.dto.response;

import com.ssafy.ssafymate.dto.UserDto.UserBoardDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserListResponseDto {

    @ApiModelProperty(name="유저 리스트", example = "[]")
    List<UserBoardDto> users;

    @ApiModelProperty(name="전체 페이지", example = "5")
    Integer totalPage;

    @ApiModelProperty(name="현재 페이지", example = "1")
    Integer nowPage;

    @ApiModelProperty(name="엘리멘탈 개수", example = "45")
    Long totalElement;

    public static UserListResponseDto of(List<UserBoardDto> users, String project, Integer totalPage, Integer nowPage, Long totalElement){

        UserListResponseDto res = new UserListResponseDto();
        res.setUsers(users);
        res.setTotalPage(totalPage);
        res.setNowPage(nowPage);
        res.setTotalElement(totalElement);
        return res;

    }

}
