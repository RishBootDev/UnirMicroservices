package com.rishbootdev.profileservice.service;


import com.rishbootdev.profileservice.dto.PersonDto;
import com.rishbootdev.profileservice.dto.ProfileDto;
import com.rishbootdev.profileservice.entity.Education;
import com.rishbootdev.profileservice.entity.Person;
import com.rishbootdev.profileservice.repository.EducationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationService {

    private final EducationRepository educationRepository;
    private final ModelMapper modelMapper;

    public List<PersonDto> getProfilesByEducation(Long id) {

        Education education = educationRepository.findById(id)
                .orElseThrow(()->new RuntimeException("not found education"));

        List<Person> list = education.getPersons();

        List<PersonDto> ans = new ArrayList<>();
        modelMapper.map(list, ans);

        return ans;

    }


}
