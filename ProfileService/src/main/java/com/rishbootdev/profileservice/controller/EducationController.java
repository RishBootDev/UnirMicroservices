package com.rishbootdev.profileservice.controller;


import com.rishbootdev.profileservice.dto.PersonDTO;
import com.rishbootdev.profileservice.service.EducationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/education")
@RequiredArgsConstructor
public class EducationController {

    private final EducationService educationService;

    @GetMapping("/getProfiles/{institute}")
    public ResponseEntity<List<PersonDTO>> getProfilesByEducation(@PathVariable String institute) {
       return ResponseEntity.ok(educationService.byInstituteName(institute));
    }
}
