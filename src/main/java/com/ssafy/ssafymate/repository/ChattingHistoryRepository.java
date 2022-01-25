package com.ssafy.ssafymate.repository;

import com.ssafy.ssafymate.dto.ChatDto.ContentList;
import com.ssafy.ssafymate.entity.ChattingHistory;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChattingHistoryRepository extends JpaRepository<ChattingHistory, Long> {

    @Query(value = "select CH.id, CH.sender_id, U.student_name username, CH.content, CH.sent_time\n" +
            "from chatting_history AS CH\n" +
            "join user as U\n" +
            "on U.id = CH.sender_id\n" +
            "WHERE CH.chatting_room_id = :roomId", nativeQuery = true)
    List<ContentList> getHistoryList(Pageable pageable, @Param("roomId") String roomId);
//    Optional<List<ContentList>> getHistoryList(@Param("roomId") String roomId);

    @Query(value = "select count(*) from chatting_history where chatting_room_id = :roomId", nativeQuery = true)
    int countByChattingRoomId(@Param("roomId") String roomId);

    @Modifying
    @Query(value = "insert into chatting_history(chatting_room_id, sender_id, sent_time, content) values (:roomId, :senderId, :sentTime, :content)", nativeQuery = true)
    @Transactional
    int saveHistory(@Param("roomId") String roomId, @Param("senderId") Long senderId, @Param("sentTime") String sentTime, @Param("content") String content);
}
