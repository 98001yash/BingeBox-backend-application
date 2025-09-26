package com.company.BingeBox_backend_application.user_service.service.Impl;

import com.company.BingeBox_backend_application.user_service.dtos.FavoriteItemDto;
import com.company.BingeBox_backend_application.user_service.entities.FavoriteItem;
import com.company.BingeBox_backend_application.user_service.entities.UserProfile;
import com.company.BingeBox_backend_application.user_service.exceptions.ResourceNotFoundException;
import com.company.BingeBox_backend_application.user_service.repository.FavoriteRepository;
import com.company.BingeBox_backend_application.user_service.repository.UserProfileRepository;
import com.company.BingeBox_backend_application.user_service.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserProfileRepository userProfileRepository;

    @Override
    public FavoriteItemDto addToFavorite(Long userId, Long contentId, String contentType) {
       log.info("Adding contentId={} contentType={} to favorites for userId={}",contentId, contentType, userId);

       UserProfile user = userProfileRepository.findById(userId)
               .orElseThrow(()->new ResourceNotFoundException("User not found with id"+userId));

        FavoriteItem item = FavoriteItem.builder()
                .contentId(contentId)
                .contentType(contentType)
                .user(user)
                .build();

        FavoriteItem saved = favoriteRepository.save(item);

        log.info("Successfully added contentId={} to userId={} favorites",contentId, userId);
        return mapToDto(saved);
    }

    @Override
    public void removeFromFavorites(Long userId, Long contentId, String contentType) {
        log.info("Removing contentId={} contentType={} from favorites for userId={}", contentId, contentType, userId);

        FavoriteItem item = favoriteRepository.findByUser_UserIdAndContentIdAndContentType(userId, contentId, contentType)
                .orElseThrow(() -> new ResourceNotFoundException("Favorite item not found for contentId=" + contentId));

        favoriteRepository.delete(item);
        log.info("Removed contentId={} from userId={} favorites", contentId, userId);
    }

    @Override
    public List<FavoriteItemDto> getUserFavorites(Long userId) {
        log.info("Fetching favorites for userId={}", userId);

        List<FavoriteItem> items = favoriteRepository.findByUser_UserId(userId);

        return items.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private FavoriteItemDto mapToDto(FavoriteItem item) {
        return FavoriteItemDto.builder()
                .id(item.getId())
                .contentId(item.getContentId())
                .contentType(item.getContentType())
                .build();
    }
}
