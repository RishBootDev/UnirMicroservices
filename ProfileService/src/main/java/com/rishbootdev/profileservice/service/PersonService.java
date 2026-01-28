package com.rishbootdev.profileservice.service;

import com.rishbootdev.profileservice.entity.*;
import com.rishbootdev.profileservice.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
    private final ProjectRepository projectRepository;
    private final SkillRepository skillRepository;
    private final CertificationRepository certificationRepository;
    private final RecommendationRepository recommendationRepository;


    public Person getProfile(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found"));
    }


    public ContactInfo addOrUpdateContact(Long id, ContactInfo contactInfo) {
        Person person = getProfile(id);
        person.setContactInfo(contactInfo);
        personRepository.save(person);
        return contactInfo;
    }

    public void removeContact(Long id) {
        Person person = getProfile(id);
        person.setContactInfo(null);
        personRepository.save(person);
    }

    public Experience addExperience(Long id, Experience experience) {
        Person person = getProfile(id);
        experience.setPerson(person);
        return experienceRepository.save(experience);
    }

    public void removeExperience(Long experienceId) {
        experienceRepository.deleteById(experienceId);
    }


    public Education addEducation(Long id, Education education) {
        Person person = getProfile(id);
        education.setPerson(person);
        return educationRepository.save(education);
    }

    public void removeEducation(Long educationId) {
        educationRepository.deleteById(educationId);
    }


    public Project addProject(Long id, Project project) {
        Person person = getProfile(id);
        project.setPerson(person);
        return projectRepository.save(project);
    }

    public void removeProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }

    public Skill addSkill(Long id, Skill skill) {
        Person person = getProfile(id);
        skill.setPerson(person);
        return skillRepository.save(skill);
    }

    public void removeSkill(Long skillId) {
        skillRepository.deleteById(skillId);
    }


    public Certification addCertification(Long id, Certification certification) {
        Person person = getProfile(id);
        certification.setPerson(person);
        return certificationRepository.save(certification);
    }

    public void removeCertification(Long certificationId) {
        certificationRepository.deleteById(certificationId);
    }


    public Recommendation addRecommendation(Long id, Recommendation recommendation) {
        Person person = getProfile(id);
        recommendation.setPerson(person);
        return recommendationRepository.save(recommendation);
    }

    public void removeRecommendation(Long recommendationId) {
        recommendationRepository.deleteById(recommendationId);
    }

    public List<Language> addLanguage(Long id, Language language) {
        Person person = getProfile(id);
        person.getLanguages().add(language);
        personRepository.save(person);
        return person.getLanguages();
    }

    public void removeLanguage(Long id, Language language) {
        Person person = getProfile(id);
        person.getLanguages().remove(language);
        personRepository.save(person);
    }


    public Set<String> addKeyword(Long id, String keyword) {
        Person person = getProfile(id);
        person.getTopKeywords().add(keyword);
        personRepository.save(person);
        return person.getTopKeywords();
    }

    public void removeKeyword(Long id, String keyword) {
        Person person = getProfile(id);
        person.getTopKeywords().remove(keyword);
        personRepository.save(person);
    }
}
