package com.ssafy.ssafymate.repository;

import com.ssafy.ssafymate.entity.TeamStack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamStackRepository extends JpaRepository<TeamStack,Long> {

    List<TeamStack> findByTeamId (Long teamId);

    Integer deleteByTeamId (Long teamId);

}
