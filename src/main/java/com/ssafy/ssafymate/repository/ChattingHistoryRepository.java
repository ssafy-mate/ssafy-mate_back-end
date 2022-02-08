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

    @Query(value = "select CH.id, CH.room_id, CH.sender_id, U.student_name username, CH.content, CH.sent_time\n" +
            "from chatting_history AS CH\n" +
            "join user as U\n" +
            "on U.id = CH.sender_id\n" +
            "WHERE CH.room_id = :roomId AND CH.id < :id\n" +
            "ORDER BY CH.id DESC \n"+
            "limit :size", nativeQuery = true)
    List<ContentList> getHistoryList(@Param("roomId") String roomId, @Param("id") Long id, @Param("size") int size);

    @Query(value = "select CH.id, CH.room_id, CH.sender_id, U.student_name username, CH.content, CH.sent_time\n" +
            "from chatting_history AS CH\n" +
            "join user as U\n" +
            "on U.id = CH.sender_id\n" +
            "WHERE CH.room_id = :roomId\n" +
            "ORDER BY CH.id DESC\n "+
            "limit :size", nativeQuery = true)
    List<ContentList> getLatestHistoryList(@Param("roomId") String roomId, @Param("size") int size);

}
