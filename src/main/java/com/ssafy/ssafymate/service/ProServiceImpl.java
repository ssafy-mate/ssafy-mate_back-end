package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.entity.Team;
import com.ssafy.ssafymate.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("proService")
public class ProServiceImpl implements ProService {

    @Autowired
    TeamRepository teamRepository;

    @Override
    public List<Team> findTeam(String campus, String project, String projectTrack) {
        if (campus == null && projectTrack == null) {
            return teamRepository.findAllByProject(project);
        } else if (campus != null && projectTrack == null) {
            return teamRepository.findAllByCampusAndProject(campus, project);
        } else if (campus == null && projectTrack != null) {
            return teamRepository.findAllByProjectAndProjectTrack(project, projectTrack);
        } else {
            return teamRepository.findAllByCampusAndProjectAndProjectTrack(campus, project, projectTrack);
        }

    }

}
