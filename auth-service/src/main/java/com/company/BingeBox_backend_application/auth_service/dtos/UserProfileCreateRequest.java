package com.company.BingeBox_backend_application.auth_service.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileCreateRequest {

    private Long userid;
    private String displayName;
    private String avatarUrl;
}
