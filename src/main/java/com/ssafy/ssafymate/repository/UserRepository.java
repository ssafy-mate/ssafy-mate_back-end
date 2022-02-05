package com.ssafy.ssafymate.repository;

import com.ssafy.ssafymate.dto.UserDto.UserBoardInterface;
import com.ssafy.ssafymate.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByStudentNumberAndStudentName(String studentNumber, String studentName);

    @Query(value = "SELECT " +
            " (CASE WHEN UT.TEAM_ID IS NULL THEN 'False' ELSE 'True' END) AS BELONG_TO_TEAM," +
            " U.ID, U.PROFILE_IMG, U.CAMPUS, U.SSAFY_TRACK, U.STUDENT_NAME, U.JOB1, U.JOB2, U.GITHUB_URL, U.COMMON_PROJECT_TRACK, U.Specialization_project_track   " +
            "FROM " +
            "   (SELECT * FROM USER " +
            "   WHERE campus LIKE %:campus% " +
            "   AND ssafy_track LIKE %:ssafyTrack% " +
            "   AND (CASE WHEN (:project='공통 프로젝트') " +
            "       THEN (common_project_track LIKE %:projectTrack% OR (common_project_track IS NULL)) " +
            "       WHEN (:project='특화 프로젝트') " +
            "       THEN (specialization_project_track LIKE %:projectTrack% OR specialization_project_track IS NULL)" +
            "       ELSE " +
            "       1 " +
            "       END " +
            "       )" +
            "   AND JOB1 LIKE %:job% " +
            "   AND STUDENT_NAME LIKE %:studentName% " +
            "   AND CASE WHEN 0 NOT IN (:userStacks) " +
            "   THEN (ID IN (SELECT USER_ID FROM USER_STACK WHERE TECH_STACK_CODE IN (:userStacks))) " +
            "       " +
            "   ELSE 1 END " +
            " ) U "     +
            "LEFT JOIN " +
            "   (select * from USER_TEAM " +
            "   WHERE TEAM_ID IN " +
            "       (SELECT ID FROM TEAM " +
            "       WHERE PROJECT=:project)) " +
            "UT " +
            "ON U.id = UT.user_id " +
            "WHERE 1=(CASE WHEN :exclusion=true THEN " +
            "       (CASE WHEN UT.TEAM_ID IS NULL THEN 1 ELSE 0 END) " +
            "   ELSE 1 END)"
            ,
            nativeQuery = true
    )
    Page<UserBoardInterface> findStudentListJPQL(Pageable pageable,
                                   @Param("campus") String campus,
                                   @Param("ssafyTrack") String ssafyTrack,
                                   @Param("job")String job,
                                   @Param("studentName") String studentName,
                                   @Param("project") String project,
                                   @Param("projectTrack") String projectTrack,
                                   @Param("userStacks") List<Long> userStacks,
                                   @Param("exclusion") Boolean exclusion
    );

    @Transactional
    @Modifying
    @Query(value = "" +
            "UPDATE USER SET " +
            "COMMON_PROJECT_TRACK=:projectTrack " +
            "WHERE id=:userId"
            , nativeQuery = true)
    int updateCommonProjectTrack(@Param("userId") Long userId, @Param("projectTrack") String projectTrack);

    @Transactional
    @Modifying
    @Query(value = "" +
            "UPDATE USER SET " +
            "SPECIALIZATION_PROJECT_TRACK=:projectTrack " +
            "WHERE id=:userId"
            , nativeQuery = true)
    int updateSpecialProjectTrack(@Param("userId") Long userId, @Param("projectTrack") String projectTrack);
}