package com.ssafy.ssafymate.repository;

import com.ssafy.ssafymate.entity.UserTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTeamRepository extends JpaRepository<UserTeam,Long> {
}
