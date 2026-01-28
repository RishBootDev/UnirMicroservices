package com.rishbootdev.profileservice.entity;


import jakarta.persistence.*;
import lombok.*;
import java.time.Year;
import java.util.List;

@Entity
@Table(name = "educations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String institution;
    private String degree;
    private String fieldOfStudy;
    private Year startYear;
    private Year endYear;
    private String grade;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToMany
    private List<Person> persons;
}

