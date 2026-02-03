package com.rishbootdev.profileservice.controller;


import com.rishbootdev.profileservice.dto.CompanyDTO;
import com.rishbootdev.profileservice.dto.InstitutionDTO;
import com.rishbootdev.profileservice.service.CompanyService;
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
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {

    private final ModelMapper modelMapper;
    private final CompanyService service;

    @GetMapping("/search")
    public ResponseEntity<List<CompanyDTO>> searchCompany(
            @RequestParam String name) {

        return ResponseEntity.ok(service.searchCompanyByName(name));
    }
}
