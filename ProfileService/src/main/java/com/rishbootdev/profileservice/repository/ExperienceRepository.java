package com.rishbootdev.profileservice.repository;

import com.rishbootdev.profileservice.entity.Experience;
import com.rishbootdev.profileservice.entity.Person;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {

    @Query("""
        SELECT DISTINCT e.person
        FROM Experience e
        WHERE LOWER(e.company.name) = LOWER(:companyName)
    """)
    List<Person> findPersonsByCompanyName(
            @Param("companyName") String companyName
    );
}
