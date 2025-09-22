package com.company.BingeBox_backend_application.auth_service.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {


    private Long id;
    private String name;
    private String email;
}
