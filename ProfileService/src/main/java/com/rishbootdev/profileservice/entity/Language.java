package com.rishbootdev.profileservice.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class Language implements Serializable {

    private String name;
    private String proficiency;
}
