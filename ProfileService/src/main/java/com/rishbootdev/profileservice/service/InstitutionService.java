package com.rishbootdev.profileservice.service;


import com.rishbootdev.profileservice.dto.InstitutionDTO;
import com.rishbootdev.profileservice.entity.Institution;
import com.rishbootdev.profileservice.repository.InstitutionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstitutionService {

    private final InstitutionRepository repository;
    private final ModelMapper modelMapper;

    public List<InstitutionDTO> getAllInstitution() {
        return repository.findAll()
                .stream()
                .map(institution ->
                        modelMapper.map(institution, InstitutionDTO.class))
                .toList();
    }

    public List<InstitutionDTO> searchInstitutionsByName(String keyword) {
        return repository.findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(inst -> modelMapper.map(inst, InstitutionDTO.class))
                .toList();
    }


}
