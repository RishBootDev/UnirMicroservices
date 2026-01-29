package com.rishbootdev.profileservice.entity;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(
        name = "companies",
        indexes = @Index(name = "idx_company_name", columnList = "name")
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String website;
    private String industry;
    private String logoUrl;
}


