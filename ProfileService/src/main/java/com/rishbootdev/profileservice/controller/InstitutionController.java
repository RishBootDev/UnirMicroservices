package com.rishbootdev.profileservice.controller;


import com.rishbootdev.profileservice.dto.InstitutionDTO;
import com.rishbootdev.profileservice.service.InstitutionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/institution")
@RequiredArgsConstructor
public class InstitutionController {

    private final ModelMapper modelMapper;
    private final InstitutionService service;

    @GetMapping("/search")
    public ResponseEntity<List<InstitutionDTO>> searchInstitutions(
            @RequestParam String name) {

        return ResponseEntity.ok(service.searchInstitutionsByName(name));
    }

}
