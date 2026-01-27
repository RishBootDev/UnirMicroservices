package com.rishbootdev.profileservice.controller;

import com.rishbootdev.profileservice.entity.*;
import com.rishbootdev.profileservice.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;


    @GetMapping("/{id}")
    public ResponseEntity<Person> getProfile(@PathVariable Long id) {
        return ResponseEntity.ok(personService.getProfile(id));
    }


    @PostMapping("/{id}/contact")
    public ResponseEntity<ContactInfo> addOrUpdateContact(
            @PathVariable Long id,
            @RequestBody ContactInfo contactInfo) {

        return ResponseEntity.ok(
                personService.addOrUpdateContact(id, contactInfo)
        );
    }

    @DeleteMapping("/{id}/contact")
    public ResponseEntity<Void> removeContact(@PathVariable Long id) {
        personService.removeContact(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{id}/experience")
    public ResponseEntity<Experience> addExperience(
            @PathVariable Long id,
            @RequestBody Experience experience) {

        return ResponseEntity.ok(
                personService.addExperience(id, experience)
        );
    }

    @DeleteMapping("/experience/{experienceId}")
    public ResponseEntity<Void> removeExperience(@PathVariable Long experienceId) {
        personService.removeExperience(experienceId);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{id}/education")
    public ResponseEntity<Education> addEducation(
            @PathVariable Long id,
            @RequestBody Education education) {

        return ResponseEntity.ok(
                personService.addEducation(id, education)
        );
    }

    @DeleteMapping("/education/{educationId}")
    public ResponseEntity<Void> removeEducation(@PathVariable Long educationId) {
        personService.removeEducation(educationId);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{id}/project")
    public ResponseEntity<Project> addProject(
            @PathVariable Long id,
            @RequestBody Project project) {

        return ResponseEntity.ok(
                personService.addProject(id, project)
        );
    }

    @DeleteMapping("/project/{projectId}")
    public ResponseEntity<Void> removeProject(@PathVariable Long projectId) {
        personService.removeProject(projectId);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{id}/skill")
    public ResponseEntity<Skill> addSkill(
            @PathVariable Long id,
            @RequestBody Skill skill) {

        return ResponseEntity.ok(
                personService.addSkill(id, skill)
        );
    }

    @DeleteMapping("/skill/{skillId}")
    public ResponseEntity<Void> removeSkill(@PathVariable Long skillId) {
        personService.removeSkill(skillId);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{id}/certification")
    public ResponseEntity<Certification> addCertification(
            @PathVariable Long id,
            @RequestBody Certification certification) {

        return ResponseEntity.ok(
                personService.addCertification(id, certification)
        );
    }

    @DeleteMapping("/certification/{certificationId}")
    public ResponseEntity<Void> removeCertification(@PathVariable Long certificationId) {
        personService.removeCertification(certificationId);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{id}/recommendation")
    public ResponseEntity<Recommendation> addRecommendation(
            @PathVariable Long id,
            @RequestBody Recommendation recommendation) {

        return ResponseEntity.ok(
                personService.addRecommendation(id, recommendation)
        );
    }

    @DeleteMapping("/recommendation/{recommendationId}")
    public ResponseEntity<Void> removeRecommendation(@PathVariable Long recommendationId) {
        personService.removeRecommendation(recommendationId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/language")
    public ResponseEntity<List<Language>> addLanguage(
            @PathVariable Long id,
            @RequestBody Language language) {

        return ResponseEntity.ok(
                personService.addLanguage(id, language)
        );
    }

    @DeleteMapping("/{id}/language")
    public ResponseEntity<Void> removeLanguage(
            @PathVariable Long id,
            @RequestBody Language language) {

        personService.removeLanguage(id, language);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{id}/keyword")
    public ResponseEntity<Set<String>> addKeyword(
            @PathVariable Long id,
            @RequestBody String keyword) {

        return ResponseEntity.ok(
                personService.addKeyword(id, keyword)
        );
    }

    @DeleteMapping("/{id}/keyword")
    public ResponseEntity<Void> removeKeyword(
            @PathVariable Long id,
            @RequestBody String keyword) {

        personService.removeKeyword(id, keyword);
        return ResponseEntity.noContent().build();
    }
}
