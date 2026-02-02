package com.rishbootdev.profileservice.entity;

import com.rishbootdev.profileservice.enums.EmploymentType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.Set;


@Entity
@Table(name = "experiences")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    private LocalDate startDate;
    private LocalDate endDate;
    private String location;

    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ElementCollection
    @CollectionTable(
            name = "experience_technologies",
            joinColumns = @JoinColumn(name = "experience_id")
    )
    @Column(name = "technology")
    private Set<String> technologies;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;
}


