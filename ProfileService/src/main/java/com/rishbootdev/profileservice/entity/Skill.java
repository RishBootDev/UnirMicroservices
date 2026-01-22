package com.rishbootdev.profileservice.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "skills")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer endorsementCount;

    @Enumerated(EnumType.STRING)
    private ProficiencyLevel proficiency;

    @ManyToOne
    private Person person;
}

