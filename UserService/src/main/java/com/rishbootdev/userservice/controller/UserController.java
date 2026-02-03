package com.rishbootdev.userservice.controller;

import com.rishbootdev.userservice.entity.User;
import com.rishbootdev.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/getUserId/{email}")
    public ResponseEntity<Long> getUserIdFromEmail(@PathVariable String email) {


        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("The user is not found with this email "+email));

        return ResponseEntity.ok(user.getId());
    }

}
