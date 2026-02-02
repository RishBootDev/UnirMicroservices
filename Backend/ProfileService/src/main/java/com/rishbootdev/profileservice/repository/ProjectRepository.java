package com.rishbootdev.profileservice.repository;

import com.rishbootdev.profileservice.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
