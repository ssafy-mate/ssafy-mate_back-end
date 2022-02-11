package com.ssafy.ssafymate.repository;

import com.ssafy.ssafymate.dto.ChatDto.RoomList;
import com.ssafy.ssafymate.entity.ChattingRoom;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChattingRoomRepository extends JpaRepository<ChattingRoom, String> {

    @Query(value = "SELECT UR.*,CH.content, CH.sent_time \n" +
            "FROM \n" +
            "    (SELECT CR.room_id, U.id user_id, U.student_name username, U.profile_img, U.email \n" +
            "    FROM\n" +
            "        (SELECT room_id, (CASE WHEN user_id_small = :userId THEN user_id_big \n" +
            "                ELSE user_id_small END)\n" +
            "                 user_id \n" +
            "        FROM chatting_room \n" +
            "        WHERE user_id_small = :userId OR user_id_big=:userId) CR\n" +
            "    JOIN \n" +
            "        user U\n" +
            "    ON U.id = CR.user_id) UR\n" +
            "LEFT JOIN\n" +
            "    (SELECT CH1.room_id, CH1.sent_time, CH1.content\n" +
            "    FROM \n" +
            "        chatting_history CH1\n" +
            "    JOIN\n" +
            "        (SELECT room_id, MAX(sent_time) sent_time\n" +
            "        FROM chatting_history \n" +
            "        GROUP BY room_id) CH2\n" +
            "    ON CH1.room_id = CH2.room_id AND  CH1.sent_time =  CH2.sent_time) CH\n" +
            "ON UR.room_id = CH.room_id"
    , nativeQuery = true)
    Optional<List<RoomList>> getChattingRoom(@Param("userId") Long userId);

    Optional<ChattingRoom> findByRoomId(String roomId);

    @Modifying
    @Query(value = "insert into chatting_room (room_id, user_id_small, user_id_big) values (:roomId, :userIdSmall, :userIdBig)", nativeQuery = true)
    @Transactional
    int saveRoom(@Param("roomId") String roomId, @Param("userIdSmall") Long userIdSmall, @Param("userIdBig") Long userIdBig);

}
