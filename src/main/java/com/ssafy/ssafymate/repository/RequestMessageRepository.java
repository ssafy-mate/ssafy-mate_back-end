package com.ssafy.ssafymate.repository;

import com.ssafy.ssafymate.entity.RequestMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestMessageRepository extends JpaRepository<RequestMessage, String> {

    RequestMessage findById(Long id);

    List<RequestMessage> findAllByReceiverIdAndProject(Long receiverId,String Project);

    List<RequestMessage> findAllBySenderIdAndProject(Long senderId,String Project);

    @Modifying
    @Query(value = "UPDATE request_message " +
            "SET read_check = :readCheck " +
            "WHERE id = :messageId "+
            "AND read_check='pending' ",
            nativeQuery = true
    )
    Integer updateRead(@Param("messageId") Long messageId, @Param("readCheck") String readCheck);

    @Modifying
    @Query(value = "UPDATE request_message " +
            "SET read_check = 'expiration' " +
            "WHERE ((receiver_id = :userId AND type='teamRequest') OR (sender_id = :userId AND type='userRequest')) " +
            "AND project=:project " +
            "AND read_check='pending' ",
            nativeQuery = true
    )
    Integer updateReadExpirationUser(@Param("userId") Long userId,@Param("project") String project );

    @Modifying
    @Query(value = "UPDATE request_message " +
            "SET read_check = 'expiration' " +
            "WHERE team_id=:teamId " +
            "AND project=:project " +
            "AND read_check='pending' ",
            nativeQuery = true
    )
    Integer updateReadExpirationTeam(@Param("teamId") Long teamId,@Param("project") String project );

}
