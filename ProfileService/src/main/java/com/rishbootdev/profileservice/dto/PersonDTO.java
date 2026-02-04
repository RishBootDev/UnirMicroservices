package com.rishbootdev.profileservice.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String headline;
    private String location;
    private String industry;
    private String profileUrl;
    private String profilePictureUrl;

    private ContactInfoDTO contactInfo;
    private String summary;

    private List<ProjectDTO> projects;
    private List<SkillDTO> skills;
    private List<CertificationDTO> certifications;

    private List<LanguageDTO> languages;
    private Set<String> topKeywords;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
