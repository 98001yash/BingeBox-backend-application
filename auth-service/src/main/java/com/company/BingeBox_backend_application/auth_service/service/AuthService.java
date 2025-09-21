package com.company.BingeBox_backend_application.auth_service.service;

import com.company.BingeBox_backend_application.auth_service.dtos.LoginRequestDto;
import com.company.BingeBox_backend_application.auth_service.dtos.SignupRequestDto;
import com.company.BingeBox_backend_application.auth_service.dtos.UserResponseDto;
import com.company.BingeBox_backend_application.auth_service.entities.User;
import com.company.BingeBox_backend_application.auth_service.exceptions.ResourceNotFoundException;
import com.company.BingeBox_backend_application.auth_service.exceptions.RuntimeConflictException;
import com.company.BingeBox_backend_application.auth_service.repository.UserRepository;
import com.company.BingeBox_backend_application.auth_service.utils.PasswordUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
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
      log.info("Login attempt for email: {}",loginRequestDto.getEmail());

      User user = userRepository.findByEmail(loginRequestDto.getEmail())
              .orElseThrow(()-> {
                  log.warn("User not found for email: {}",loginRequestDto.getEmail());
                  throw new ResourceNotFoundException("Invalid email or password");
              });

      if(!PasswordUtils.checkPassword(loginRequestDto.getPassword(), user.getPassword())) {
          log.warn("Invalid password attempt for email: {}",loginRequestDto.getEmail());
          throw new BadCredentialsException("Invalid email or password");
      }

      String token = jwtService.generateAccessToken(user);
      log.info("Jwt generated for user id: {}",user.getId());

      return token;
    }


    public UserResponseDto getUserById(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with Id: "+userId));

        return modelMapper.map(user, UserResponseDto.class);
    }

    // fetch user from JWT token
    public UserResponseDto getCurrentUser(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("Missing or invalid Authorization header");
            throw new BadCredentialsException("Invalid or missing token");
        }

        String token = authHeader.substring(7);
        Claims claims = jwtService.extractAllClaims(token);
        Long userId = claims.get("userId", Long.class);

        log.info("Extracted userId {} from token", userId);
        return getUserById(userId);
    }
}
