package com.ssafy.ssafymate.repository;

import com.ssafy.ssafymate.entity.RequestMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestMessageRepository extends JpaRepository<RequestMessage, String> {

    RequestMessage findById(Long id);

    Optional<RequestMessage> findBySenderIdAndTeamIdAndReceiverIdAndRequestStatus(Long senderId, Long teamId, Long receiverId, String requestStatus);

    List<RequestMessage> findAllByReceiverIdAndProject(Long receiverId,String Project);

    List<RequestMessage> findAllBySenderIdAndProject(Long senderId,String Project);

    @Modifying
    @Query(value = "UPDATE request_message " +
            "SET request_status = :requestStatus " +
            "WHERE id = :messageId "+
            "AND request_status='pending' ",
            nativeQuery = true
    )
    Integer updateRead(@Param("messageId") Long messageId, @Param("requestStatus") String requestStatus);

    @Modifying
    @Query(value = "UPDATE request_message " +
            "SET request_status = 'expiration' " +
            "WHERE ((receiver_id = :userId AND type='teamRequest') OR (sender_id = :userId AND type='userRequest')) " +
            "AND project=:project " +
            "AND request_status='pending' ",
            nativeQuery = true
    )
    Integer updateReadExpirationUser(@Param("userId") Long userId,@Param("project") String project );

    @Modifying
    @Query(value = "UPDATE request_message " +
            "SET request_status = 'expiration' " +
            "WHERE team_id=:teamId " +
            "AND project=:project " +
            "AND request_status='pending' ",
            nativeQuery = true
    )
    Integer updateReadExpirationTeam(@Param("teamId") Long teamId,@Param("project") String project );

}
