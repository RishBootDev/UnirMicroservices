package com.rishbootdev.profileservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LanguageDTO {

    private String name;
    private String proficiency;
}
