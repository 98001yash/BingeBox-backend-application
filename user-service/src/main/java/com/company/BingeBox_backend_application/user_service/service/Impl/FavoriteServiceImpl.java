package com.company.BingeBox_backend_application.user_service.service.Impl;



import com.company.BingeBox_backend_application.user_service.auth.UserContextHolder;
import com.company.BingeBox_backend_application.user_service.client.CatalogClient;
import com.company.BingeBox_backend_application.user_service.dtos.FavoriteItemDto;
import com.company.BingeBox_backend_application.user_service.dtos.MovieResponseDto;
import com.company.BingeBox_backend_application.user_service.dtos.TvShowResponseDto;
import com.company.BingeBox_backend_application.user_service.entities.FavoriteItem;
import com.company.BingeBox_backend_application.user_service.entities.UserProfile;
import com.company.BingeBox_backend_application.user_service.repository.FavoriteRepository;
import com.company.BingeBox_backend_application.user_service.repository.UserProfileRepository;
import com.company.BingeBox_backend_application.user_service.service.FavoriteService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserProfileRepository userProfileRepository;
    private final CatalogClient catalogClient;

    @Override
    public FavoriteItemDto addToFavorite(Long contentId, String contentType) {
        Long userId = UserContextHolder.getCurrentUserId();
        if (userId == null) throw new RuntimeException("User not authenticated");

        UserProfile user = userProfileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Prevent duplicates
        if (favoriteRepository.findByUser_UserIdAndContentIdAndContentType(userId, contentId, contentType).isPresent()) {
            throw new RuntimeException("Content already in favorites");
        }

        FavoriteItem item = FavoriteItem.builder()
                .contentId(contentId)
                .contentType(contentType)
                .user(user)
                .build();

        FavoriteItem saved = favoriteRepository.save(item);
        return mapToDtoWithCatalog(saved);
    }

    @Override
    public void removeFromFavorite(Long contentId, String contentType) {
        Long userId = UserContextHolder.getCurrentUserId();
        FavoriteItem item = favoriteRepository
                .findByUser_UserIdAndContentIdAndContentType(userId, contentId, contentType)
                .orElseThrow(() -> new RuntimeException("Favorite item not found"));
        favoriteRepository.delete(item);
    }

    @Override
    public List<FavoriteItemDto> getUserFavorites() {
        Long userId = UserContextHolder.getCurrentUserId();
        return favoriteRepository.findByUser_UserId(userId)
                .stream()
                .map(this::mapToDtoWithCatalog)
                .collect(Collectors.toList());
    }

    // Robust mapping with Feign + error handling
    private FavoriteItemDto mapToDtoWithCatalog(FavoriteItem item) {
        MovieResponseDto movie = null;
        TvShowResponseDto tvShow = null;

        try {
            if ("MOVIE".equalsIgnoreCase(item.getContentType())) {
                movie = catalogClient.getMovieById(item.getContentId());
            } else if ("TVSHOW".equalsIgnoreCase(item.getContentType())) {
                tvShow = catalogClient.getTvShowById(item.getContentId());
            }
        } catch (FeignException e) {
            log.error("Failed to fetch catalog data for contentId={}", item.getContentId(), e);
        }

        return FavoriteItemDto.builder()
                .id(item.getId())
                .contentId(item.getContentId())
                .contentType(item.getContentType())
                .movie(movie)
                .tvShow(tvShow)
                .build();
    }
}
