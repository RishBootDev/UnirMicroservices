package com.rishbootdev.profileservice.repository;

import com.rishbootdev.profileservice.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepository extends JpaRepository<Education, Long> {
}
