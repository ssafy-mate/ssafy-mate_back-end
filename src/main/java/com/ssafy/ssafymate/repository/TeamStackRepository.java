package com.ssafy.ssafymate.repository;

import com.ssafy.ssafymate.entity.TeamStack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamStackRepository extends JpaRepository<TeamStack,Long> {
    List<TeamStack> findByTeamId (Long teamId);
    Integer deleteByTeamId (Long teamId);
}
