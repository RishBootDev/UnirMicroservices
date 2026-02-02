package com.rishbootdev.profileservice.service;

import com.rishbootdev.profileservice.auth.UserContextHolder;
import com.rishbootdev.profileservice.dto.PersonDTO;
import com.rishbootdev.profileservice.entity.*;
import com.rishbootdev.profileservice.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class PersonService {

    private final PersonRepository personRepository;
    private final ExperienceRepository experienceRepository;
    private final EducationRepository educationRepository;
    private final ModelMapper modelMapper;

    private Person getCurrentPerson() {
        Long userId = UserContextHolder.getCurrentUserId();
        return personRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Person not found for current user"));
    }

    public Person getProfile() {
        return getCurrentPerson();
    }

    public ContactInfo addOrUpdateContact(ContactInfo contactInfo) {
        Person person = getCurrentPerson();
        person.setContactInfo(contactInfo);
        personRepository.save(person);
        return contactInfo;
    }

    public void removeContact() {
        Person person = getCurrentPerson();
        person.setContactInfo(null);
        personRepository.save(person);
    }


    public Experience addExperience(Experience experience) {
        Person person = getCurrentPerson();
        experience.setPerson(person);
        return experienceRepository.save(experience);
    }

    public void removeExperience(Long experienceId) {
        experienceRepository.deleteById(experienceId);
    }


    public Education addEducation(Education education) {
        Person person = getCurrentPerson();
        education.setPerson(person);
        return educationRepository.save(education);
    }

    public void removeEducation(Long educationId) {
        educationRepository.deleteById(educationId);
    }


    public Project addProject(Project project) {
        Person person = getCurrentPerson();
        person.getProjects().add(project);
        personRepository.save(person);
        return project;
    }

    public void removeProject(Long projectId) {
        Person person = getCurrentPerson();
        person.getProjects().removeIf(p -> p.getId().equals(projectId));
        personRepository.save(person);
    }


    public Skill addSkill(Skill skill) {
        Person person = getCurrentPerson();
        person.getSkills().add(skill);
        personRepository.save(person);
        return skill;
    }

    public void removeSkill(Long skillId) {
        Person person = getCurrentPerson();
        person.getSkills().removeIf(s -> s.getId().equals(skillId));
        personRepository.save(person);
    }


    public Certification addCertification(Certification certification) {
        Person person = getCurrentPerson();
        person.getCertifications().add(certification);
        personRepository.save(person);
        return certification;
    }

    public void removeCertification(Long certificationId) {
        Person person = getCurrentPerson();
        person.getCertifications().removeIf(c -> c.getId().equals(certificationId));
        personRepository.save(person);
    }


    public List<Language> addLanguage(Language language) {
        Person person = getCurrentPerson();
        person.getLanguages().add(language);
        personRepository.save(person);
        return person.getLanguages();
    }

    public void removeLanguage(Language language) {
        Person person = getCurrentPerson();
        person.getLanguages().remove(language);
        personRepository.save(person);
    }


    public Set<String> addKeyword(String keyword) {
        Person person = getCurrentPerson();
        person.getTopKeywords().add(keyword);
        personRepository.save(person);
        return person.getTopKeywords();
    }

    public void removeKeyword(String keyword) {
        Person person = getCurrentPerson();
        person.getTopKeywords().remove(keyword);
        personRepository.save(person);
    }

    public void addPerson(PersonDTO personDTO) {

        Person person = modelMapper.map(personDTO, Person.class);
        personRepository.save(person);

    }
}
