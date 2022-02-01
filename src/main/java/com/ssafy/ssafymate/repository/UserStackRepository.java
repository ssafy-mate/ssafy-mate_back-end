package com.ssafy.ssafymate.repository;

import com.ssafy.ssafymate.entity.UserStack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserStackRepository extends JpaRepository<UserStack, Long> {
    List<UserStack> findAllByUserId(Long userId);
    Integer deleteByUserId(Long userId);
}
