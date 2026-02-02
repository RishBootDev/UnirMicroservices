package com.rishbootdev.userservice.controller;

import com.rishbootdev.userservice.entity.User;
import com.rishbootdev.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/getUserId/{email}")
    public ResponseEntity<Long> getUserIdFromEmail(String email){

        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("The user is not found with this email"));

        return ResponseEntity.ok(user.getId());
    }
}
