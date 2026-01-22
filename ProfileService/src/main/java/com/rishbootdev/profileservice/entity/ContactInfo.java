package com.rishbootdev.profileservice.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "contact_infos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String phone;
    private String website;
    private String github;
    private String stackOverflow;
    private String twitter;
    private String linkedin;
}

