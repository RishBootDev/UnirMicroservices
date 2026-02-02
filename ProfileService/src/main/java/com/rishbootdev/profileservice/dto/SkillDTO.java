package com.rishbootdev.profileservice.dto;

import com.rishbootdev.profileservice.enums.ProficiencyLevel;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkillDTO {

    private String name;
    private Integer endorsementCount;
    private ProficiencyLevel proficiency;
}
