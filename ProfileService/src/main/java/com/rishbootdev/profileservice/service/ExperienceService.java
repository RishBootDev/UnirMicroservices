package com.rishbootdev.profileservice.service;


import com.rishbootdev.profileservice.dto.PersonDTO;
import com.rishbootdev.profileservice.entity.Person;
import com.rishbootdev.profileservice.repository.ExperienceRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final ModelMapper modelMapper;


    public List<PersonDTO> byCompanyName(String name){

        List<Person> list = experienceRepository.findPersonsByCompanyName(name);
        return list.stream()
                .map(person -> modelMapper.map(person, PersonDTO.class))
                .toList();
    }
}
