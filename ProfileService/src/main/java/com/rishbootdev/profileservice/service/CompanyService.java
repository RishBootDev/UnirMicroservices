package com.rishbootdev.profileservice.service;


import com.rishbootdev.profileservice.dto.CompanyDTO;
import com.rishbootdev.profileservice.dto.InstitutionDTO;
import com.rishbootdev.profileservice.repository.CompanyRepository;
import com.rishbootdev.profileservice.repository.InstitutionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository repository;
    private final ModelMapper modelMapper;

    public List<CompanyDTO> getAllCompany() {
        return repository.findAll()
                .stream()
                .map(company ->
                        modelMapper.map(company, CompanyDTO.class))
                .toList();
    }
    public List<CompanyDTO> searchCompanyByName(String keyword) {
        return repository.findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(comp -> modelMapper.map(comp, CompanyDTO.class))
                .toList();
    }


}
