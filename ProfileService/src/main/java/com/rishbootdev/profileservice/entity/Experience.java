package com.rishbootdev.profileservice.entity;

import com.rishbootdev.profileservice.enums.EmploymentType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "experiences")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    private Company company;

    private LocalDate startDate;
    private LocalDate endDate;
    private String location;

    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ElementCollection
    private Set<String> technologies;

    @ManyToOne
    private Person person;
}

