package com.rishbootdev.profileservice.repository;

import com.rishbootdev.profileservice.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person,Long> {
}
