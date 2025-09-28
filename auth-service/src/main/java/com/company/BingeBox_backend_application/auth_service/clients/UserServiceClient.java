package com.company.BingeBox_backend_application.auth_service.clients;


import com.company.BingeBox_backend_application.auth_service.dtos.UserProfileCreateRequest;
import com.company.BingeBox_backend_application.auth_service.dtos.UserProfileDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", path = "/users/profiles")
public interface UserServiceClient {

    @PostMapping
    UserProfileDto createUserProfile(@RequestBody UserProfileCreateRequest request);
}
