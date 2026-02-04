package com.rishbootdev.profileservice.repository;

import com.rishbootdev.profileservice.entity.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InstitutionRepository extends JpaRepository<Institution, Long> {

    List<Institution> findByNameContainingIgnoreCase(String name);

    Optional<Institution> findByNameIgnoreCase(String institutionName);
}
