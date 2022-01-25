package com.ssafy.ssafymate.repository;

import com.ssafy.ssafymate.entity.Team;
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



    // 팀 정보 상세 보기
    @Query(value = "select t.ID ,  t.BACKEND_RECRUITMENT,  t.CAMPUS,  t.CREATE_DATE_TIME,   t.FRONTEND_RECRUITMENT,  t.INTRODUCTION,  t.NOTICE,  t.PROJECT," +
            "  t.PROJECT_TRACK,  t.TEAM_IMG,  t.TEAM_NAME,  t.TOTAL_RECRUITMENT,  t.OWNER_ID, uut.FRONTEND_HEADCOUNT,  uut.BACKEND_HEADCOUNT,  uut.TOTAL_HEADCOUNT" +
            " from (select * from team where id = :teamId) t " +
            "join \n" +
            "(select ut.team_id, \n" +
            "count(case when u.job1 like '%Front%' then 1 end) as frontend_headcount,\n" +
            "count(case when u.job1 like '%Back%' then 1 end) as backend_headcount,\n" +
            "count(*) as total_headcount \n" +
            "from user u \n" +
            "join (select * from user_team where team_id = :teamId) ut\n" +
            "on u.id = ut.user_id\n" +
            "group by ut.team_id) uut \n" +
            "on t.id = uut.team_id"
            ,nativeQuery = true)
    Optional<Team> findByIdJQL(@Param("teamId") Long teamId);


    // 팀 조회 (일반)
    @Query(value = "select t.ID ,  t.BACKEND_RECRUITMENT,  t.CAMPUS,  t.CREATE_DATE_TIME,   t.FRONTEND_RECRUITMENT,  t.INTRODUCTION,  t.NOTICE,  t.PROJECT," +
            "  t.PROJECT_TRACK,  t.TEAM_IMG,  t.TEAM_NAME,  t.TOTAL_RECRUITMENT,  t.OWNER_ID, uut.FRONTEND_HEADCOUNT,  uut.BACKEND_HEADCOUNT,  uut.TOTAL_HEADCOUNT " +
            " from (select * from team where project=:project " +
            "            and project_track=:projectTrack " +
            "            and team_name like %:teamName%) t " +
            "join \n" +
                "(select ut.team_id, \n" +
                "count(case when u.job1 like '%Front%' then 1 end) as frontend_headcount, " +
                "count(case when u.job1 like '%Back%' then 1 end) as backend_headcount, " +
                "count(*) as total_headcount \n" +
                "from user u \n" +
                "join (select * from user_team ) ut\n" +
                "on u.id = ut.user_id\n" +
                "group by ut.team_id) uut \n" +
            "on t.id = uut.team_id"
            ,nativeQuery = true)
    Optional<List<Team>> findALLJQL(@Param("project") String project,
                                    @Param("projectTrack") String projectTrack,
                                    @Param("teamName") String teamName);

    // 팀 리스트 조회(스택 검색)
    @Query(value = "select t.ID ,  t.BACKEND_RECRUITMENT,  t.CAMPUS,  t.CREATE_DATE_TIME,   t.FRONTEND_RECRUITMENT,  t.INTRODUCTION,  t.NOTICE,  t.PROJECT," +
            "  t.PROJECT_TRACK,  t.TEAM_IMG,  t.TEAM_NAME,  t.TOTAL_RECRUITMENT,  t.OWNER_ID, uut.FRONTEND_HEADCOUNT,  uut.BACKEND_HEADCOUNT,  uut.TOTAL_HEADCOUNT" +
            " from (select * from team where project=:project " +
            "            and project_track=:projectTrack " +
            "            and team_name like %:teamName% " +
            "            and id in " +
            "            (select team_id from team_stack where tech_stack_name in (:teamStacks))) t " +
            "join \n" +
            "(select ut.team_id, \n" +
            "count(case when u.job1 like '%Front%' then 1 end) as frontend_headcount,\n" +
            "count(case when u.job1 like '%Back%' then 1 end) as backend_headcount,\n" +
            "count(*) as total_headcount \n" +
            "from user u \n" +
            "join (select * from user_team ) ut\n" +
            "on u.id = ut.user_id\n" +
            "group by ut.team_id) uut \n" +
            "on t.id = uut.team_id"
            ,nativeQuery = true)
    Optional<List<Team>> findALLByteamStackJQL(@Param("project") String project,
                                               @Param("projectTrack") String projectTrack,
                                               @Param("teamName") String teamName,
                                               @Param("teamStacks") List<String> teamStacks);

}
