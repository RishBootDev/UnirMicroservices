package com.rishbootdev.profileservice.service;


import com.rishbootdev.profileservice.dto.PersonDTO;
import com.rishbootdev.profileservice.entity.Person;
import com.rishbootdev.profileservice.repository.EducationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationService {

    private final EducationRepository educationRepository;
    private final ModelMapper modelMapper;


    public List<PersonDTO> byInstituteName(String name){

        List<Person> list = educationRepository.findPersonsByInstitutionName(name);
        return list.stream()
                .map(person -> modelMapper.map(person, PersonDTO.class))
                .toList();
    }
}
