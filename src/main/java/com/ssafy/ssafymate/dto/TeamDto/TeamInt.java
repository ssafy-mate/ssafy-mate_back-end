package com.ssafy.ssafymate.dto.TeamDto;

import java.time.LocalDateTime;

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
