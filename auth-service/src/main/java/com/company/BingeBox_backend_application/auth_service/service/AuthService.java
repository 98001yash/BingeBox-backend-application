package com.company.BingeBox_backend_application.auth_service.service;

import com.company.BingeBox_backend_application.auth_service.dtos.LoginRequestDto;
import com.company.BingeBox_backend_application.auth_service.dtos.SignupRequestDto;
import com.company.BingeBox_backend_application.auth_service.dtos.UserResponseDto;
import com.company.BingeBox_backend_application.auth_service.entities.User;
import com.company.BingeBox_backend_application.auth_service.exceptions.RuntimeConflictException;
import com.company.BingeBox_backend_application.auth_service.repository.UserRepository;
import com.company.BingeBox_backend_application.auth_service.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;

    // signup new user
    public UserResponseDto signup(SignupRequestDto signupRequestDto){
        log.info("Attempting to signup for email: {}",signupRequestDto.getEmail());

        if(userRepository.existsByEmail(signupRequestDto.getEmail())){
            log.warn("Email already exists: {}",signupRequestDto.getEmail());
            throw new RuntimeConflictException("Email already registered");
        }

        User user = modelMapper.map(signupRequestDto, User.class);
        user.setPassword(PasswordUtils.hashPassword(signupRequestDto.getPassword()));

        User savedUser = userRepository.save(user);
        log.info("User registered successfully with id: {}",savedUser.getId());

        return modelMapper.map(savedUser, UserResponseDto.class);
    }


    public String login(LoginRequestDto loginRequestDto){

    }
}
