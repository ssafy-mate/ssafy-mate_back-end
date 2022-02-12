package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.entity.ProjectDeadline;

public interface ProjectDeadlineService {
    
    // 해당 프로젝트 마감 기한 확인
    ProjectDeadline findProjectDeadline(String project);

}
