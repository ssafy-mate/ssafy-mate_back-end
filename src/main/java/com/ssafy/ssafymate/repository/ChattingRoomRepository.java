package com.ssafy.ssafymate.repository;

import com.ssafy.ssafymate.entity.ChattingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChattingRoomRepository extends JpaRepository<ChattingRoom, String> {
}
