package com.rishbootdev.postservice.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Hiring {

    @Id
    private Long id;

    private boolean isHiring;

    @Column(nullable = false)
    private String field;

}
