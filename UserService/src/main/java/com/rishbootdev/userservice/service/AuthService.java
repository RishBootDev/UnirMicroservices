package com.rishbootdev.userservice.service;

import com.rishbootdev.userservice.client.ConnectionClient;
import com.rishbootdev.userservice.dto.LoginRequestDto;
import com.rishbootdev.userservice.dto.SignupRequestDto;
import com.rishbootdev.userservice.dto.UserDto;
import com.rishbootdev.userservice.entity.User;
import com.rishbootdev.userservice.exceptions.BadRequestException;
import com.rishbootdev.userservice.exceptions.ResourceNotFoundException;
import com.rishbootdev.userservice.repository.UserRepository;
import com.rishbootdev.userservice.utils.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final ConnectionClient connectionClient;

    @Transactional
    public UserDto signUp(SignupRequestDto signupRequestDto) throws BadRequestException {
        boolean exists = userRepository.existsByEmail(signupRequestDto.getEmail());
        if(exists) {
            throw new BadRequestException("User already exists, cannot signup again.");
        }

        User user = modelMapper.map(signupRequestDto, User.class);
        user.setPassword(PasswordUtil.hashPassword(signupRequestDto.getPassword()));

        User savedUser = userRepository.save(user);
        

            log.info("Syncing user with ConnectionService for userId: {}", savedUser.getId());
            connectionClient.syncPerson(savedUser.getId(), savedUser.getName());

        
        return modelMapper.map(savedUser, UserDto.class);
    }

    public String login(LoginRequestDto loginRequestDto) throws ResourceNotFoundException, BadRequestException {
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: "+loginRequestDto.getEmail()));

        boolean isPasswordMatch = PasswordUtil.checkPassword(loginRequestDto.getPassword(), user.getPassword());

        if(!isPasswordMatch) {
            throw new BadRequestException("Incorrect password");
        }

        return jwtService.generateAccessToken(user);
    }
}
