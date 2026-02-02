package com.rishbootdev.profileservice.repository;

import com.rishbootdev.profileservice.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}
