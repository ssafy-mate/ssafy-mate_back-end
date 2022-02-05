package com.ssafy.ssafymate.repository;

import com.ssafy.ssafymate.entity.RequestMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestMessageRepository extends JpaRepository<RequestMessage, String> {
}
