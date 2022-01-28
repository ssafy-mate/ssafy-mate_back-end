package com.ssafy.ssafymate.repository;

import com.ssafy.ssafymate.entity.TechStack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechStackRepository extends JpaRepository<TechStack,Long> {
}
