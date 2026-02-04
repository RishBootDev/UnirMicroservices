package com.rishbootdev.profileservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectsCertsDTO {
    private List<ProjectDTO> projects;
    private List<CertificationDTO> certifications;
}
