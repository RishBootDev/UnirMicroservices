package com.rishbootdev.profileservice.dto;

import lombok.*;
import java.time.Year;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EducationDTO {

    private InstitutionDTO institution;

    private String degree;
    private String fieldOfStudy;
    private Year startYear;
    private Year endYear;
    private String grade;
    private String description;
}
