package com.ssafy.ssafymate.repository;

import com.ssafy.ssafymate.dto.TeamDto.TeamInt;
import com.ssafy.ssafymate.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team,Long> {

    // 참여 하고 있는 팀이 있는 지 조회
    @Query(value = "select * from team " +
            "where id in " +
            "(select team_id from user_team where user_id = :userId) " +
            "and project = :selectedProject "
            ,nativeQuery = true)
    Optional<Team> belongToTeam(@Param("selectedProject") String selectedProject,
                                @Param("userId") Long userId);

    // 팀 아이디와 유저 아이디로 팀 조회(팀장 인지 확인)
    @Query(value = "select * from team " +
            "where id = :teamId " +
            "and owner_id = :userId "
            ,nativeQuery = true)
    Optional<Team> findByTeamIdAndUserIdJQL(@Param("teamId") Long teamId,
                                            @Param("userId") Long userId);


    // 팀 리스트 조회(스택 검색)
    @Query(value = "select t.id ,  t.backend_recruitment, t.create_date_time,  t.campus,   t.frontend_recruitment,  t.notice,  t.project, " +
            "t.project_track,  t.team_img,  t.team_name,  t.total_recruitment, uut.frontend_headcount,  uut.backend_headcount,  uut.total_headcount " +
            "from (select * from team where campus LIKE %:campus% " +
            "           AND project=:project " +
            "           AND project_track Like %:projectTrack% " +
            "           AND team_name like %:teamName% " +
            "           AND " +
            "           CASE WHEN 0 NOT IN (:teamStacks) " +
            "           THEN " +
            "               id in (select team_id from team_stack where tech_stack_code in (:teamStacks)) " +
            "           ELSE 1 END " +
            "           ) t " +
            "JOIN \n" +
            "           (SELECT ut.team_id, \n" +
            "           count(case when u.job1 like '%Front%' then 1 end) as frontend_headcount,\n" +
            "           count(case when u.job1 like '%Back%' then 1 end) as backend_headcount,\n" +
            "           count(*) as total_headcount \n" +
            "           from user u \n" +
            "           join (select * from user_team ) ut\n" +
            "           on u.id = ut.user_id\n" +
            "           group by ut.team_id) uut \n" +
            "on t.id = uut.team_id " +
            "where t.backend_recruitment - uut.backend_headcount >= :back " +
            "and t.frontend_recruitment - uut.frontend_headcount >= :front " +
            "and t.total_recruitment - uut.total_headcount >= :total"
            ,nativeQuery = true)
    Page<TeamInt> findALLByTeamStackJQL(Pageable pageable,
                                        @Param("campus") String campus,
                                        @Param("project") String project,
                                        @Param("projectTrack") String projectTrack,
                                        @Param("teamName") String teamName,
                                        @Param("front") Integer front,
                                        @Param("back") Integer back,
                                        @Param("total") Integer total,
                                        @Param("teamStacks") List<Long> teamStacks);

    @Query(value = "select " +
            "   (case when t.total_recruitment > ut.total_headcount then 'true'" +
            "   else 'false' end ) as is_recruit " +
            "from " +
            "   (select * from team where id=:teamId) t " +
            "join " +
            "   (select team_id, count(*) as total_headcount " +
            "   from user_team " +
            "   where team_id=:teamId " +
            "   group by team_id) ut " +
            "on t.id = ut.team_id"
            ,nativeQuery = true)
    Boolean isRecruit(@Param("teamId") Long teamId);
}
