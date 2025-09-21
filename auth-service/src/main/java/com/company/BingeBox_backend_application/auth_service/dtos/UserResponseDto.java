package com.company.BingeBox_backend_application.auth_service.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDto {


    private Long id;
    private String name;
    private String email;
    private String token;
}
