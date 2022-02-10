package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.entity.ProjectDeadline;
import com.ssafy.ssafymate.repository.ProjectDeadlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("projectDeadlineService")
public class ProjectDeadlineServiceImpl implements ProjectDeadlineService{

    @Autowired
    ProjectDeadlineRepository projectDeadlineRepository;

    @Override
    public ProjectDeadline findProjectDeadline(String project) {
        return projectDeadlineRepository.findByProject(project).orElse(null);
    }
}
