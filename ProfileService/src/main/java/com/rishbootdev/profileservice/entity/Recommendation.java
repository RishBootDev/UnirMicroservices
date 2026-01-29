package com.rishbootdev.profileservice.entity;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "recommendations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromName;
    private String fromTitle;
    private LocalDate date;

    @Column(columnDefinition = "TEXT")
    private String text;

}

