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
import java.util.Optional;
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
    private final jakarta.persistence.EntityManager entityManager;

    private Optional<Person> getCurrentPerson() {
        Long userId = UserContextHolder.getCurrentUserId();
        return personRepository.findByUserId(userId);
    }

    public PersonDTO getProfile() {
        return getCurrentPerson()
                .map(person -> modelMapper.map(person, PersonDTO.class))
                .orElse(null);
    }

    public void addPerson(PersonDTO dto) {
        Long userId = UserContextHolder.getCurrentUserId();
        if (userId == null) {
            userId = dto.getUserId();
        }
        if (userId == null) {
            userId = userClient.getUserIdFromEmail(dto.getEmail());
        }

        final Long finalUserId = userId;
        Optional<Person> existingPerson = personRepository.findByUserId(finalUserId);

        if (existingPerson.isEmpty()) {
            Person person = new Person();
            modelMapper.map(dto, person);
            person.setId(null);
            person.setUserId(finalUserId);
            person.setCreatedAt(java.time.LocalDateTime.now());
            person.setUpdatedAt(java.time.LocalDateTime.now());

            if (person.getExperiences() != null) person.getExperiences().forEach(e -> e.setPerson(person));
            if (person.getEducations() != null) person.getEducations().forEach(e -> e.setPerson(person));
            
            personRepository.save(person);
        } else {
            Person person = existingPerson.get();

            person.setFirstName(dto.getFirstName());
            person.setLastName(dto.getLastName());
            person.setHeadline(dto.getHeadline());
            person.setLocation(dto.getLocation());
            person.setIndustry(dto.getIndustry());
            person.setSummary(dto.getSummary());
            person.setProfilePictureUrl(dto.getProfilePictureUrl());
            person.setUpdatedAt(java.time.LocalDateTime.now());

            if (dto.getContactInfo() != null) {
                if (person.getContactInfo() == null) person.setContactInfo(new ContactInfo());
                modelMapper.map(dto.getContactInfo(), person.getContactInfo());
            }
        }
    }

    public void updateSummary(String summary) {
        Person person = getCurrentPerson()
                .orElseThrow(() -> new RuntimeException("Person not found"));
        person.setSummary(summary);
        person.setUpdatedAt(java.time.LocalDateTime.now());
    }


    public void setExperiences(List<ExperienceDTO> dtos) {
        Person person = getCurrentPerson()
                .orElseThrow(() -> new RuntimeException("Person not found"));

        person.getExperiences().clear();
        entityManager.flush();

        for (ExperienceDTO dto : dtos) {
            Experience exp = modelMapper.map(dto, Experience.class);
            exp.setPerson(person);
            if (dto.getCompany() != null && dto.getCompany().getName() != null) {
                String cName = dto.getCompany().getName().trim();
                Company company = companyRepository.findByNameIgnoreCase(cName)
                        .orElseGet(() -> companyRepository.save(new Company(null, cName, null, null, null)));
                exp.setCompany(company);
            }
            person.getExperiences().add(exp);
        }
        person.setUpdatedAt(java.time.LocalDateTime.now());
    }


    public void setEducations(List<EducationDTO> dtos) {
        Person person = getCurrentPerson()
                .orElseThrow(() -> new RuntimeException("Person not found"));

        person.getEducations().clear();
        entityManager.flush();

        for (EducationDTO dto : dtos) {
            Education edu = modelMapper.map(dto, Education.class);
            edu.setPerson(person);
            if (dto.getInstitution() != null && dto.getInstitution().getName() != null) {
                String iName = dto.getInstitution().getName().trim();
                Institution institution = institutionRepository.findByNameIgnoreCase(iName)
                        .orElseGet(() -> institutionRepository.save(new Institution(null, iName, null, null,null,null)));
                edu.setInstitution(institution);
            }
            person.getEducations().add(edu);
        }
        person.setUpdatedAt(java.time.LocalDateTime.now());
    }

    public void setSkillsAndLanguages(List<SkillDTO> skillDtos, List<LanguageDTO> langDtos) {
        Person person = getCurrentPerson()
                .orElseThrow(() -> new RuntimeException("Person not found"));

        person.getSkills().clear();
        for (SkillDTO dto : skillDtos) {
            Skill skill = modelMapper.map(dto, Skill.class);
            person.getSkills().add(skill);
        }

        person.getLanguages().clear();
        for (LanguageDTO dto : langDtos) {
            Language lang = modelMapper.map(dto, Language.class);
            person.getLanguages().add(lang);
        }
        
        person.setUpdatedAt(java.time.LocalDateTime.now());
    }

    public void setProjectsAndCertifications(List<ProjectDTO> projDtos, List<CertificationDTO> certDtos) {
        Person person = getCurrentPerson()
                .orElseThrow(() -> new RuntimeException("Person not found"));

        person.getProjects().clear();
        for (ProjectDTO dto : projDtos) {
            Project proj = modelMapper.map(dto, Project.class);
            person.getProjects().add(proj);
        }

        person.getCertifications().clear();
        for (CertificationDTO dto : certDtos) {
            Certification cert = modelMapper.map(dto, Certification.class);
            person.getCertifications().add(cert);
        }
        
        person.setUpdatedAt(java.time.LocalDateTime.now());
    }

    public List<PersonDTO> getProfileSByName(String name) {
        return personRepository.findAll().stream()
                .filter(p ->
                        (p.getFirstName() != null && p.getFirstName().toLowerCase().contains(name.toLowerCase())) ||
                                (p.getLastName() != null && p.getLastName().toLowerCase().contains(name.toLowerCase()))
                )
                .map(p -> modelMapper.map(p, PersonDTO.class))
                .toList();
    }

    public PersonDTO getProfileByUserId(Long userId) {
        Person person = personRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Person not found with userId: " + userId));
        return modelMapper.map(person, PersonDTO.class);
    }

    public ContactInfoDTO addOrUpdateContact(ContactInfoDTO dto) {
        Person person = getCurrentPerson()
                .orElseThrow(() -> new RuntimeException("Person not found"));
        ContactInfo contactInfo = modelMapper.map(dto, ContactInfo.class);
        person.setContactInfo(contactInfo);
        return modelMapper.map(
                personRepository.save(person).getContactInfo(),
                ContactInfoDTO.class
        );
    }

    public void removeContact() {
        Person person = getCurrentPerson()
                .orElseThrow(() -> new RuntimeException("Person not found"));
        person.setContactInfo(null);
        personRepository.save(person);
    }

    public ExperienceDTO addExperience(ExperienceDTO dto) {
        Experience experience = modelMapper.map(dto, Experience.class);

        String companyName = dto.getCompany().getName().trim();
        Company company = companyRepository.findByNameIgnoreCase(companyName)
                .orElseThrow(() -> new IllegalStateException("Company not found: " + companyName));

        experience.setCompany(company);
        experience.setPerson(getCurrentPerson()
                .orElseThrow(() -> new RuntimeException("Person not found")));

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
        education.setPerson(getCurrentPerson()
                .orElseThrow(() -> new RuntimeException("Person not found")));

        return modelMapper.map(
                educationRepository.save(education),
                EducationDTO.class
        );
    }

    public void removeEducation(Long educationId) {
        educationRepository.deleteById(educationId);
    }

    public ProjectDTO addProject(ProjectDTO dto) {
        Person person = getCurrentPerson()
                .orElseThrow(() -> new RuntimeException("Person not found"));
        Project project = modelMapper.map(dto, Project.class);
        person.getProjects().add(project);
        personRepository.save(person);
        return modelMapper.map(project, ProjectDTO.class);
    }

    public void removeProject(Long projectId) {
        Person person = getCurrentPerson()
                .orElseThrow(() -> new RuntimeException("Person not found"));
        person.getProjects().removeIf(p -> p.getId().equals(projectId));
        personRepository.save(person);
    }

    public SkillDTO addSkill(SkillDTO dto) {
        Person person = getCurrentPerson()
                .orElseThrow(() -> new RuntimeException("Person not found"));
        Skill skill = modelMapper.map(dto, Skill.class);
        person.getSkills().add(skill);
        personRepository.save(person);
        return modelMapper.map(skill, SkillDTO.class);
    }

    public void removeSkill(Long skillId) {
        Person person = getCurrentPerson()
                .orElseThrow(() -> new RuntimeException("Person not found"));
        person.getSkills().removeIf(s -> s.getId().equals(skillId));
        personRepository.save(person);
    }

    public CertificationDTO addCertification(CertificationDTO dto) {
        Person person = getCurrentPerson()
                .orElseThrow(() -> new RuntimeException("Person not found"));
        Certification certification = modelMapper.map(dto, Certification.class);
        person.getCertifications().add(certification);
        personRepository.save(person);
        return modelMapper.map(certification, CertificationDTO.class);
    }

    public void removeCertification(Long certificationId) {
        Person person = getCurrentPerson()
                .orElseThrow(() -> new RuntimeException("Person not found"));
        person.getCertifications().removeIf(c -> c.getId().equals(certificationId));
        personRepository.save(person);
    }

    public List<LanguageDTO> addLanguage(LanguageDTO dto) {
        Person person = getCurrentPerson()
                .orElseThrow(() -> new RuntimeException("Person not found"));
        Language language = modelMapper.map(dto, Language.class);
        person.getLanguages().add(language);
        personRepository.save(person);
        return person.getLanguages().stream()
                .map(l -> modelMapper.map(l, LanguageDTO.class))
                .toList();
    }

    public void removeLanguage(LanguageDTO dto) {
        Person person = getCurrentPerson()
                .orElseThrow(() -> new RuntimeException("Person not found"));
        Language language = modelMapper.map(dto, Language.class);
        person.getLanguages().remove(language);
        personRepository.save(person);
    }

    public Set<String> addKeyword(String keyword) {
        Person person = getCurrentPerson()
                .orElseThrow(() -> new RuntimeException("Person not found"));
        person.getTopKeywords().add(keyword);
        personRepository.save(person);
        return person.getTopKeywords();
    }

    public void removeKeyword(String keyword) {
        Person person = getCurrentPerson()
                .orElseThrow(() -> new RuntimeException("Person not found"));
        person.getTopKeywords().remove(keyword);
        personRepository.save(person);
    }
}
