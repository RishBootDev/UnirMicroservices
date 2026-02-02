package com.rishbootdev.profileservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactInfoDTO {

    private String email;
    private String phone;
    private String website;
    private String github;
    private String twitter;
    private String linkedin;
}
