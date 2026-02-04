package com.rishbootdev.profileservice.repository;

import com.rishbootdev.profileservice.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findByNameContainingIgnoreCase(String keyword);

    Optional<Company> findByNameIgnoreCase(String name);

}
