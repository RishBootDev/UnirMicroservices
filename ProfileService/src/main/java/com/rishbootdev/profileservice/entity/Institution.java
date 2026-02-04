package com.rishbootdev.profileservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "institutions",
        indexes = @Index(name = "idx_institution_name", columnList = "name")
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String city;
    private String country;

    private String websiteUrl;
    private String logoUrl;

}

