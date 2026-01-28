package com.rishbootdev.profileservice.repository;

import com.rishbootdev.profileservice.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
}
