package com.rishbootdev.profileservice.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificationDTO {

    private String name;
    private LocalDate date;
    private String credentialUrl;
}
