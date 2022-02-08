package com.ssafy.ssafymate.repository;

import com.ssafy.ssafymate.entity.UserTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserTeamRepository extends JpaRepository<UserTeam,Long> {
    @Transactional
    Integer deleteByUserIdAndTeamId(Long userId, Long teamId);

    Optional<UserTeam> findByUserIdAndTeamId(Long userId, Long teamId);
}
