package com.rishbootdev.profileservice.repository;

import com.rishbootdev.profileservice.entity.Person;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person,Long> {

    @Query("""
        SELECT DISTINCT p
        FROM Person p
        JOIN p.skills s
        WHERE LOWER(s.name) = LOWER(:skillName)
    """)
    List<Person> findPersonsBySkillName(
            @Param("skillName") String skillName
    );
}
