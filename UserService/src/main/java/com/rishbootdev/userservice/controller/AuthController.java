package com.rishbootdev.userservice.controller;


import com.rishbootdev.userservice.dto.LoginRequestDto;
import com.rishbootdev.userservice.dto.SignupRequestDto;
import com.rishbootdev.userservice.dto.UserDto;
import com.rishbootdev.userservice.exceptions.BadRequestException;
import com.rishbootdev.userservice.exceptions.ResourceNotFoundException;
import com.rishbootdev.userservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignupRequestDto signupRequestDto) throws BadRequestException {
        UserDto userDto = authService.signUp(signupRequestDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto) throws BadRequestException, ResourceNotFoundException {
        String token = authService.login(loginRequestDto);
        return ResponseEntity.ok(token);
    }
}
