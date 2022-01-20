package com.ssafy.ssafymate.repository;

import com.ssafy.ssafymate.entity.Team;
import com.ssafy.ssafymate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team,Long> {
    Team findByUserId (User user);

    @Query(value = "select * from team " +
            "where project=:project " +
            "and project_track=:projectTrack " +
            "and id in " +
            "(select team_id from team_stack where stack_name in (:teamStacks))"
            , nativeQuery = true)
    Optional<List<Team>> findAllByProjectAndProjectTrackAndTechStacksInJQL(@Param("project") String project,
                                                                          @Param("projectTrack") String projectTrack,
                                                                          @Param("teamStacks") List<String> teamStacks);
    @Query(value = "select * from team " +
            "where id in " +
            "(select team_id from user_team where user_id = :userId) " +
            "and project = :selectedProject "
            ,nativeQuery = true)
    Optional<Team> belongToTeam(@Param("selectedProject") String selectedProject,
                                @Param("userId") Long userId);

}
