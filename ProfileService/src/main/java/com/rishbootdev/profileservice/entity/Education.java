package com.rishbootdev.profileservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Year;

@Entity
@Table(name = "educations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "institution_id", nullable = false)
    private Institution institution;

    private String degree;
    private String fieldOfStudy;
    private Year startYear;
    private Year endYear;
    private String grade;

    @Column(columnDefinition = "TEXT")
    private String description;
}
