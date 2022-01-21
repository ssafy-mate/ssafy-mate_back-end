package com.ssafy.ssafymate.repository;

import com.ssafy.ssafymate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findEmailByStudentNumberAndStudentName(String studentNumber, String studentName);
}
