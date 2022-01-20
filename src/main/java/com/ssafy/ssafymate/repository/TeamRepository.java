package com.ssafy.ssafymate.repository;

import com.ssafy.ssafymate.entity.Team;
import com.ssafy.ssafymate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team,Long> {
    Team findByUserId (User user);
}
