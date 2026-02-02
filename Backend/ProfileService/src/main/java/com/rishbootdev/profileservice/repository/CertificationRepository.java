package com.rishbootdev.profileservice.repository;

import com.rishbootdev.profileservice.entity.Certification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificationRepository extends JpaRepository<Certification, Long> {
}
