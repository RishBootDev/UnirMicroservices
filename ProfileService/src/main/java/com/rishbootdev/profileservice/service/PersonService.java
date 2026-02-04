package com.rishbootdev.profileservice.service;

import com.rishbootdev.profileservice.auth.UserContextHolder;
import com.rishbootdev.profileservice.client.UserClient;
import com.rishbootdev.profileservice.dto.*;
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
    private final CompanyRepository companyRepository;
    private final InstitutionRepository institutionRepository;
    private final ModelMapper modelMapper;
    private final UserClient userClient;

    private Person getCurrentPerson() {
        Long userId = UserContextHolder.getCurrentUserId();
        return personRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Person not found"));
    }

    public PersonDTO getProfile() {
        return modelMapper.map(getCurrentPerson(), PersonDTO.class);
    }

    public void addPerson(PersonDTO dto) {
        Person person = modelMapper.map(dto, Person.class);
        Long userId = userClient.getUserIdFromEmail(dto.getEmail());
        person.setUserId(userId);
        personRepository.save(person);
    }

    public List<PersonDTO> getProfileSByName(String name) {
        return personRepository.findAll().stream()
                .filter(p ->
                        p.getFirstName().toLowerCase().contains(name.toLowerCase()) ||
                                p.getLastName().toLowerCase().contains(name.toLowerCase())
                )
                .map(p -> modelMapper.map(p, PersonDTO.class))
                .toList();
    }

    public ContactInfoDTO addOrUpdateContact(ContactInfoDTO dto) {
        Person person = getCurrentPerson();
        ContactInfo contactInfo = modelMapper.map(dto, ContactInfo.class);
        person.setContactInfo(contactInfo);
        return modelMapper.map(
                personRepository.save(person).getContactInfo(),
                ContactInfoDTO.class
        );
    }

    public void removeContact() {
        Person person = getCurrentPerson();
        person.setContactInfo(null);
        personRepository.save(person);
    }

    public ExperienceDTO addExperience(ExperienceDTO dto) {
        Experience experience = modelMapper.map(dto, Experience.class);

        String companyName = dto.getCompany().getName().trim();
        Company company = companyRepository.findByNameIgnoreCase(companyName)
                .orElseThrow(() -> new IllegalStateException("Company not found: " + companyName));

        experience.setCompany(company);
        experience.setPerson(getCurrentPerson());

        return modelMapper.map(
                experienceRepository.save(experience),
                ExperienceDTO.class
        );
    }

    public void removeExperience(Long experienceId) {
        experienceRepository.deleteById(experienceId);
    }

    public EducationDTO addEducation(EducationDTO dto) {
        Education education = modelMapper.map(dto, Education.class);

        String institutionName = dto.getInstitution().getName().trim();
        Institution institution = institutionRepository.findByNameIgnoreCase(institutionName)
                .orElseThrow(() -> new IllegalStateException("Institution not found: " + institutionName));

        education.setInstitution(institution);
        education.setPerson(getCurrentPerson());

        return modelMapper.map(
                educationRepository.save(education),
                EducationDTO.class
        );
    }

    public void removeEducation(Long educationId) {
        educationRepository.deleteById(educationId);
    }

    public ProjectDTO addProject(ProjectDTO dto) {
        Person person = getCurrentPerson();
        Project project = modelMapper.map(dto, Project.class);
        person.getProjects().add(project);
        personRepository.save(person);
        return modelMapper.map(project, ProjectDTO.class);
    }

    public void removeProject(Long projectId) {
        Person person = getCurrentPerson();
        person.getProjects().removeIf(p -> p.getId().equals(projectId));
        personRepository.save(person);
    }

    public SkillDTO addSkill(SkillDTO dto) {
        Person person = getCurrentPerson();
        Skill skill = modelMapper.map(dto, Skill.class);
        person.getSkills().add(skill);
        personRepository.save(person);
        return modelMapper.map(skill, SkillDTO.class);
    }

    public void removeSkill(Long skillId) {
        Person person = getCurrentPerson();
        person.getSkills().removeIf(s -> s.getId().equals(skillId));
        personRepository.save(person);
    }

    public CertificationDTO addCertification(CertificationDTO dto) {
        Person person = getCurrentPerson();
        Certification certification = modelMapper.map(dto, Certification.class);
        person.getCertifications().add(certification);
        personRepository.save(person);
        return modelMapper.map(certification, CertificationDTO.class);
    }

    public void removeCertification(Long certificationId) {
        Person person = getCurrentPerson();
        person.getCertifications().removeIf(c -> c.getId().equals(certificationId));
        personRepository.save(person);
    }

    public List<LanguageDTO> addLanguage(LanguageDTO dto) {
        Person person = getCurrentPerson();
        Language language = modelMapper.map(dto, Language.class);
        person.getLanguages().add(language);
        personRepository.save(person);
        return person.getLanguages().stream()
                .map(l -> modelMapper.map(l, LanguageDTO.class))
                .toList();
    }

    public void removeLanguage(LanguageDTO dto) {
        Person person = getCurrentPerson();
        Language language = modelMapper.map(dto, Language.class);
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
}
