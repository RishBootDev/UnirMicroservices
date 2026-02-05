package com.rishbootdev.postsservice.dto;

import lombok.Data;

@Data
public class PersonDto {

    private Long id;
    private Long userId;
    private String name; // Keeping for compatibility if used elsewhere, but mainly using first/last
    private String firstName;
    private String lastName;
    private String headline;
    private String profilePictureUrl;
}
