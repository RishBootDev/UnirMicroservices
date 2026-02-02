package com.rishbootdev.profileservice.dto;

import lombok.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDTO {

    private String name;
    private String role;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private String repoUrl;
    private String demoUrl;
    private Set<String> technologies;
}
