package com.rishbootdev.profileservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDTO {

    private String name;
    private String website;
    private String industry;
    private String logoUrl;
}
