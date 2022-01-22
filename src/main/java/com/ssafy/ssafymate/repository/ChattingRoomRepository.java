package com.ssafy.ssafymate.repository;

import com.ssafy.ssafymate.entity.ChattingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChattingRoomRepository extends JpaRepository<ChattingRoom, String> {

//    @Query(value = "select * from u")
}
