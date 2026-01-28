package com.rishbootdev.profileservice.repository;

import com.rishbootdev.profileservice.entity.Education;
import com.rishbootdev.profileservice.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {

}
