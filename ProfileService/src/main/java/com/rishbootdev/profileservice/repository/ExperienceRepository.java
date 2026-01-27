package com.rishbootdev.profileservice.repository;

import com.rishbootdev.profileservice.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
}
