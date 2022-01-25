package com.ssafy.ssafymate.repository;

import com.ssafy.ssafymate.dto.ChatDto.RoomList;
import com.ssafy.ssafymate.entity.ChattingRoom;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.annotation.Transient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChattingRoomRepository extends JpaRepository<ChattingRoom, String> {

    @Query(value = "SELECT UR.*,CH.CONTENT, CH.SENT_TIME \n" +
            "FROM \n" +
            "    (SELECT CR.ROOM_ID, U.ID USER_ID, U.STUDENT_NAME, U.PROFILE_IMG \n" +
            "    FROM\n" +
            "        (SELECT ROOM_ID, (CASE WHEN USER_ID1 = :userId THEN USER_ID2 \n" +
            "                ELSE USER_ID1 END)\n" +
            "                 USER_ID \n" +
            "        FROM CHATTING_ROOM \n" +
            "        WHERE USER_ID1 = :userId OR USER_ID2=:userId) CR\n" +
            "    JOIN \n" +
            "        USER U\n" +
            "    ON U.ID = CR.USER_ID) UR\n" +
            "JOIN\n" +
            "    (SELECT CH1.CHATTING_ROOM_ID, CH1.SENT_TIME, CH1.CONTENT\n" +
            "    FROM \n" +
            "        CHATTING_HISTORY CH1\n" +
            "    JOIN\n" +
            "        (SELECT CHATTING_ROOM_ID, MAX(SENT_TIME) SENT_TIME\n" +
            "        FROM CHATTING_HISTORY \n" +
            "        GROUP BY CHATTING_ROOM_ID) CH2\n" +
            "    ON CH1.CHATTING_ROOM_ID = CH2.CHATTING_ROOM_ID AND  CH1.SENT_TIME =  CH2.SENT_TIME) CH\n" +
            "ON UR. ROOM_ID = CH.CHATTING_ROOM_ID"
    , nativeQuery = true)
    Optional<List<RoomList>> getChattingRoom(@Param("userId") Long userId);

    Optional<ChattingRoom> findByRoomId(String roomId);

    @Modifying
    @Query(value = "insert into chatting_room (room_id, user_id1, user_id2) values (:roomId, :userId1, :userId2)", nativeQuery = true)
    @Transactional
    int saveRoom(@Param("roomId") String roomId, @Param("userId1") Long userId1, @Param("userId2") Long userId2);
}
