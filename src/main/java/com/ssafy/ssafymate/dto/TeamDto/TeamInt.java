package com.ssafy.ssafymate.dto.TeamDto;

import com.ssafy.ssafymate.entity.Team;
import com.ssafy.ssafymate.entity.TeamStack;
import com.ssafy.ssafymate.entity.User;
import com.ssafy.ssafymate.entity.UserTeam;
import org.hibernate.annotations.NotFound;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface TeamInt {

    Long getId();
    String getTeam_img();
    String getCampus();
    String getProject();
    String getProject_track();
    String getTeam_name();
    String getNotice();

    LocalDateTime getCreate_date_time();
    Integer getTotal_recruitment();
    Integer getFrontend_recruitment();
    Integer getBackend_recruitment();
    Integer getTotal_headcount();
    Integer getFrontend_headcount();
    Integer getBackend_headcount();



}
