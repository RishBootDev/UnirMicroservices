package com.rishbootdev.profileservice.controller;


import com.rishbootdev.profileservice.dto.PersonDTO;
import com.rishbootdev.profileservice.service.ExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class ExperienceController {

    private final ExperienceService experienceService;

    @GetMapping("/getProfiles/{name}")
    public ResponseEntity<List<PersonDTO>> getPersonsByCompany(@PathVariable String name){
        return ResponseEntity.ok(experienceService.byCompanyName(name));
    }
}
