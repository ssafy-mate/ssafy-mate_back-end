package com.ssafy.ssafymate.dto.response;

import com.ssafy.ssafymate.dto.UserDto.UserBoardDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserListResponseDto {
    List<UserBoardDto> users;

    Integer totalPage;

    Integer nowPage;

    Long totalElement;

//    public static UserListResponseDto of(List<User> users, String project, Integer totalPage, Integer nowPage, Long totalElement){
//        UserListResponseDto res = new UserListResponseDto();
//        res.setUsers(UserBoardDto.of(users,project));
//        res.setTotalPage(totalPage);
//        res.setNowPage(nowPage);
//        res.setTotalElement(totalElement);
//        return res;
//    }

    public static UserListResponseDto of2(List<UserBoardDto> users, String project, Integer totalPage, Integer nowPage, Long totalElement){
        UserListResponseDto res = new UserListResponseDto();
        res.setUsers(users);
        res.setTotalPage(totalPage);
        res.setNowPage(nowPage);
        res.setTotalElement(totalElement);
        return res;
    }
}
