package com.company.BingeBox_backend_application.user_service.service;


import com.company.BingeBox_backend_application.user_service.dtos.UserProfileCreateRequest;
import com.company.BingeBox_backend_application.user_service.dtos.UserProfileDto;
import com.company.BingeBox_backend_application.user_service.entities.UserProfile;
import com.company.BingeBox_backend_application.user_service.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileDto createProfile(UserProfileCreateRequest request){
        //prevent duplicate profile

        if(userProfileRepository.findById(request.getUserid()).isPresent()){
            throw new RuntimeException("Profile already exists fro userId="+request.getUserid());
        }

        UserProfile profile = UserProfile.builder()
                .userId(request.getUserid())
                .displayName(request.getDisplayName())
                .avatarUrl(request.getAvatarUrl())
                .build();

        UserProfile saved = userProfileRepository.save(profile);

        return UserProfileDto.builder()
                .userId(saved.getUserId())
                .displayName(saved.getDisplayName())
                .avatarUrl(saved.getAvatarUrl())
                .build();
    }
}
