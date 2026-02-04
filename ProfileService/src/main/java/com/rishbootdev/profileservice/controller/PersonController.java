package com.rishbootdev.profileservice.controller;

import com.rishbootdev.profileservice.dto.*;
import com.rishbootdev.profileservice.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public ResponseEntity<PersonDTO> getProfile() {
        return ResponseEntity.ok(personService.getProfile());
    }

    @GetMapping("/byName/{name}")
    public ResponseEntity<List<PersonDTO>> getProfilesByName(@PathVariable String name) {
        return ResponseEntity.ok(personService.getProfileSByName(name));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<PersonDTO> getProfileByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(personService.getProfileByUserId(userId));
    }

    @PostMapping("/addPerson")
    public ResponseEntity<Void> addPersonDetails(@RequestBody PersonDTO personDTO) {
        personService.addPerson(personDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/summary")
    public ResponseEntity<Void> updateSummary(@RequestBody String summary) {
        personService.updateSummary(summary);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/experiences")
    public ResponseEntity<Void> setExperiences(@RequestBody List<ExperienceDTO> dtos) {
        personService.setExperiences(dtos);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/educations")
    public ResponseEntity<Void> setEducations(@RequestBody List<EducationDTO> dtos) {
        personService.setEducations(dtos);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/skills-languages")
    public ResponseEntity<Void> setSkillsAndLanguages(@RequestBody SkillsLanguagesDTO dto) {
        personService.setSkillsAndLanguages(dto.getSkills(), dto.getLanguages());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/projects-certs")
    public ResponseEntity<Void> setProjectsAndCertifications(@RequestBody ProjectsCertsDTO dto) {
        personService.setProjectsAndCertifications(dto.getProjects(), dto.getCertifications());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/contact")
    public ResponseEntity<ContactInfoDTO> addOrUpdateContact(@RequestBody ContactInfoDTO dto) {
        return ResponseEntity.ok(personService.addOrUpdateContact(dto));
    }

    @DeleteMapping("/contact")
    public ResponseEntity<Void> removeContact() {
        personService.removeContact();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/experience")
    public ResponseEntity<ExperienceDTO> addExperience(@RequestBody ExperienceDTO dto) {
        return ResponseEntity.ok(personService.addExperience(dto));
    }

    @DeleteMapping("/experience/{experienceId}")
    public ResponseEntity<Void> removeExperience(@PathVariable Long experienceId) {
        personService.removeExperience(experienceId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/education")
    public ResponseEntity<EducationDTO> addEducation(@RequestBody EducationDTO dto) {
        return ResponseEntity.ok(personService.addEducation(dto));
    }

    @DeleteMapping("/education/{educationId}")
    public ResponseEntity<Void> removeEducation(@PathVariable Long educationId) {
        personService.removeEducation(educationId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/project")
    public ResponseEntity<ProjectDTO> addProject(@RequestBody ProjectDTO dto) {
        return ResponseEntity.ok(personService.addProject(dto));
    }

    @DeleteMapping("/project/{projectId}")
    public ResponseEntity<Void> removeProject(@PathVariable Long projectId) {
        personService.removeProject(projectId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/skill")
    public ResponseEntity<SkillDTO> addSkill(@RequestBody SkillDTO dto) {
        return ResponseEntity.ok(personService.addSkill(dto));
    }

    @DeleteMapping("/skill/{skillId}")
    public ResponseEntity<Void> removeSkill(@PathVariable Long skillId) {
        personService.removeSkill(skillId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/certification")
    public ResponseEntity<CertificationDTO> addCertification(@RequestBody CertificationDTO dto) {
        return ResponseEntity.ok(personService.addCertification(dto));
    }

    @DeleteMapping("/certification/{certificationId}")
    public ResponseEntity<Void> removeCertification(@PathVariable Long certificationId) {
        personService.removeCertification(certificationId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/language")
    public ResponseEntity<List<LanguageDTO>> addLanguage(@RequestBody LanguageDTO dto) {
        return ResponseEntity.ok(personService.addLanguage(dto));
    }

    @DeleteMapping("/language")
    public ResponseEntity<Void> removeLanguage(@RequestBody LanguageDTO dto) {
        personService.removeLanguage(dto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/keyword")
    public ResponseEntity<Set<String>> addKeyword(@RequestBody String keyword) {
        return ResponseEntity.ok(personService.addKeyword(keyword));
    }

    @DeleteMapping("/keyword")
    public ResponseEntity<Void> removeKeyword(@RequestBody String keyword) {
        personService.removeKeyword(keyword);
        return ResponseEntity.noContent().build();
    }
}
