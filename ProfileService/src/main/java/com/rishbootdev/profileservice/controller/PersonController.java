package com.rishbootdev.profileservice.controller;

import com.rishbootdev.profileservice.dto.PersonDTO;
import com.rishbootdev.profileservice.entity.*;
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

    @GetMapping("")
    public ResponseEntity<Person> getProfile() {
        return ResponseEntity.ok(personService.getProfile());
    }


    @RequestMapping("/byName/{name}")
    public ResponseEntity<List<PersonDTO>> getProfileSByName(String name){
        return ResponseEntity.ok(personService.getProfileSByName(name));
    }

    @PostMapping("/addPerson")
    public ResponseEntity<Void> addPersonDetails(@RequestBody PersonDTO personDTO){

        personService.addPerson(personDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PostMapping("/contact")
    public ResponseEntity<ContactInfo> addOrUpdateContact(@RequestBody ContactInfo contactInfo) {
        return ResponseEntity.ok(personService.addOrUpdateContact(contactInfo));
    }

    @DeleteMapping("/contact")
    public ResponseEntity<Void> removeContact() {
        personService.removeContact();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/experience")
    public ResponseEntity<Experience> addExperience(@RequestBody Experience experience) {
        return ResponseEntity.ok(personService.addExperience(experience));
    }

    @DeleteMapping("/experience/{experienceId}")
    public ResponseEntity<Void> removeExperience(@PathVariable Long experienceId) {
        personService.removeExperience(experienceId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/education")
    public ResponseEntity<Education> addEducation(@RequestBody Education education) {
        return ResponseEntity.ok(personService.addEducation(education));
    }

    @DeleteMapping("/education/{educationId}")
    public ResponseEntity<Void> removeEducation(@PathVariable Long educationId) {
        personService.removeEducation(educationId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/project")
    public ResponseEntity<Project> addProject(@RequestBody Project project) {
        return ResponseEntity.ok(personService.addProject(project));
    }

    @DeleteMapping("/project/{projectId}")
    public ResponseEntity<Void> removeProject(@PathVariable Long projectId) {
        personService.removeProject(projectId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/skill")
    public ResponseEntity<Skill> addSkill(@RequestBody Skill skill) {
        return ResponseEntity.ok(personService.addSkill(skill));
    }

    @DeleteMapping("/skill/{skillId}")
    public ResponseEntity<Void> removeSkill(@PathVariable Long skillId) {
        personService.removeSkill(skillId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/certification")
    public ResponseEntity<Certification> addCertification(@RequestBody Certification certification) {
        return ResponseEntity.ok(personService.addCertification(certification));
    }

    @DeleteMapping("/certification/{certificationId}")
    public ResponseEntity<Void> removeCertification(@PathVariable Long certificationId) {
        personService.removeCertification(certificationId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/language")
    public ResponseEntity<List<Language>> addLanguage(@RequestBody Language language) {
        return ResponseEntity.ok(personService.addLanguage(language));
    }

    @DeleteMapping("/language")
    public ResponseEntity<Void> removeLanguage(@RequestBody Language language) {
        personService.removeLanguage(language);
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
