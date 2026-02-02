package com.rishbootdev.profileservice.dto;

import com.rishbootdev.profileservice.enums.EmploymentType;
import lombok.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExperienceDTO {

    private String title;

    private CompanyDTO company;

    private LocalDate startDate;
    private LocalDate endDate;
    private String location;
    private EmploymentType employmentType;
    private String description;

    private Set<String> technologies;

}
