package com.ssafy.ssafymate.dto.UserDto;


import com.ssafy.ssafymate.entity.UserStack;

import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

public interface UserBoardInterface {


    Long getId();
    String getProfile_img();
    String getCampus();
    String getSsafy_track();
    String getStudent_name();
    String getJob1();
    String getJob2();
    String getGithub_url();
    String getCommon_project_track();
    String getSpecialization_project_track();
    Boolean getBelong_to_team();

    @OneToMany
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    List<UserStack> getTechStacks();

}
