package com.rishbootdev.profileservice.dto;


import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonDTO {

    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String headline;
    private String location;
    private String industry;
    private String profileUrl;
    private String profilePictureUrl;

    private String summary;
}

