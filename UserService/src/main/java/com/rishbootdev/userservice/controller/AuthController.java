package com.rishbootdev.userservice.controller;


import com.rishbootdev.userservice.dto.LoginRequestDto;
import com.rishbootdev.userservice.dto.SignupRequestDto;
import com.rishbootdev.userservice.dto.UserDto;
import com.rishbootdev.userservice.exceptions.BadRequestException;
import com.rishbootdev.userservice.exceptions.ResourceNotFoundException;
import com.rishbootdev.userservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignupRequestDto signupRequestDto) throws BadRequestException {
        UserDto userDto = authService.signUp(signupRequestDto);
        log.info("the request came with the {}",signupRequestDto.getEmail());
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto) throws BadRequestException, ResourceNotFoundException {
        String token = authService.login(loginRequestDto);
        return ResponseEntity.ok(token);
    }
}
