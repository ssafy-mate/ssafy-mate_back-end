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
            " (CASE WHEN UT.team_id IS NULL THEN 'False' ELSE 'True' END) AS belong_to_team," +
            " U.id, U.profile_img, U.campus, U.ssafy_track, U.student_name, U.job1, U.job2, U.github_url, U.common_project_track, U.specialization_project_track   " +
            "FROM " +
            "   (SELECT * FROM user " +
            "   WHERE campus LIKE %:campus% " +
            "   AND ssafy_track LIKE %:ssafyTrack% " +
            "   AND (CASE WHEN (:project='공통 프로젝트') " +
            "       THEN (common_project_track LIKE %:projectTrack% ) " +
            "       WHEN (:project='특화 프로젝트') " +
            "       THEN (specialization_project_track LIKE %:projectTrack% )" +
            "       ELSE " +
            "       1 " +
            "       END " +
            "       )" +
            "   AND job1 LIKE %:job% " +
            "   AND student_name LIKE %:studentName% " +
            "   AND CASE WHEN 0 NOT IN (:userStacks) " +
            "   THEN (id IN (SELECT user_id FROM user_stack WHERE tech_stack_code IN (:userStacks))) " +
            "       " +
            "   ELSE 1 END " +
            " ) U "     +
            "LEFT JOIN " +
            "   (select * from user_team " +
            "   WHERE team_id IN " +
            "       (SELECT id FROM team " +
            "       WHERE project=:project)) " +
            "UT " +
            "ON U.id = UT.user_id " +
            "WHERE 1=(CASE WHEN :exclusion=true THEN " +
            "       (CASE WHEN UT.team_id IS NULL THEN 1 ELSE 0 END) " +
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
            "UPDATE user SET " +
            "common_project_track=:projectTrack " +
            "WHERE id=:userId"
            , nativeQuery = true)
    int updateCommonProjectTrack(@Param("userId") Long userId, @Param("projectTrack") String projectTrack);

    @Transactional
    @Modifying
    @Query(value = "" +
            "UPDATE user SET " +
            "specialization_project_track=:projectTrack " +
            "WHERE id=:userId"
            , nativeQuery = true)
    int updateSpecialProjectTrack(@Param("userId") Long userId, @Param("projectTrack") String projectTrack);
}