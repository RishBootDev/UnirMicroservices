package com.rishbootdev.profileservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstitutionDTO {

    private String name;
    private String city;
    private String country;
}
