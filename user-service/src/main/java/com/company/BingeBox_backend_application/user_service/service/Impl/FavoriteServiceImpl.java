package com.company.BingeBox_backend_application.user_service.service.Impl;

import com.company.BingeBox_backend_application.user_service.auth.UserContextHolder;
import com.company.BingeBox_backend_application.user_service.client.CatalogClient;
import com.company.BingeBox_backend_application.user_service.dtos.FavoriteItemDto;
import com.company.BingeBox_backend_application.user_service.dtos.MovieResponseDto;
import com.company.BingeBox_backend_application.user_service.dtos.TvShowResponseDto;
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
    private final CatalogClient catalogClient;   // Feign client for Catalog service


    @Override
    public FavoriteItemDto addToFavorite(Long contentId, String contentType) {
        Long userId = UserContextHolder.getCurrentUserId();
        if (userId == null) throw new RuntimeException("User not authenticated");

        log.info("Adding contentId={} contentType={} to favorites for userId={}", contentId, contentType, userId);

        UserProfile user = userProfileRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        FavoriteItem item = FavoriteItem.builder()
                .contentId(contentId)
                .contentType(contentType)
                .user(user)
                .build();

        FavoriteItem saved = favoriteRepository.save(item);

        log.info("Successfully added contentId={} to userId={} favorites", contentId, userId);
        return mapToDtoWithCatalogData(saved);
    }

    @Override
    public void removeFromFavorites(Long contentId, String contentType) {
        Long userId = UserContextHolder.getCurrentUserId();
        if (userId == null) throw new RuntimeException("User not authenticated");

        log.info("Removing contentId={} contentType={} from favorites for userId={}", contentId, contentType, userId);

        FavoriteItem item = favoriteRepository
                .findByUser_UserIdAndContentIdAndContentType(userId, contentId, contentType)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Favorite item not found for contentId=" + contentId + " and type=" + contentType));

        favoriteRepository.delete(item);
        log.info("Removed contentId={} from userId={} favorites", contentId, userId);
    }

    @Override
    public List<FavoriteItemDto> getUserFavorites() {
        Long userId = UserContextHolder.getCurrentUserId();
        if (userId == null) throw new RuntimeException("User not authenticated");

        log.info("Fetching favorites for userId={}", userId);

        List<FavoriteItem> items = favoriteRepository.findByUser_UserId(userId);

        return items.stream()
                .map(this::mapToDtoWithCatalogData)
                .collect(Collectors.toList());
    }

    // Mapping entity -> DTO + enrich with catalog data
    private FavoriteItemDto mapToDtoWithCatalogData(FavoriteItem item) {
        FavoriteItemDto dto = new FavoriteItemDto();
        dto.setId(item.getId());
        dto.setContentId(item.getContentId());
        dto.setContentType(item.getContentType());

        try {
            MovieResponseDto movie = catalogClient.getMovieById(item.getContentId());
            if (movie == null) {
                log.warn("Catalog returned null for contentId={}", item.getContentId());
            } else {
                log.info("Catalog returned movie: {}", movie.getTitle());
            }
            dto.setMovie(movie);
        } catch (Exception e) {
            log.error("Failed to fetch catalog data for contentId={}", item.getContentId(), e);
            dto.setMovie(null);
        }

        return dto;
    }


}
