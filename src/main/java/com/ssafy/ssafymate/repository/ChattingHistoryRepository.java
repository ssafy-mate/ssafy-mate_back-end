package com.ssafy.ssafymate.repository;

import com.ssafy.ssafymate.entity.ChattingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChattingHistoryRepository extends JpaRepository<ChattingHistory, Long> {
}
