package com.rishbootdev.profileservice.repository;

import com.rishbootdev.profileservice.entity.Education;
import com.rishbootdev.profileservice.entity.Person;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {


    @Query("""
        SELECT DISTINCT e.person
        FROM Education e
        WHERE LOWER(e.institution.name) = LOWER(:institutionName)
    """)
    List<Person> findPersonsByInstitutionName(
            @Param("institutionName") String institutionName
    );
}
