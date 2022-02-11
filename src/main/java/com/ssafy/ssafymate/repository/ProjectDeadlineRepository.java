package com.ssafy.ssafymate.repository;

import com.ssafy.ssafymate.entity.ProjectDeadline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectDeadlineRepository extends JpaRepository<ProjectDeadline, String> {

    Optional<ProjectDeadline> findByProject(String project);

}
