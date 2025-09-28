package com.company.BingeBox_backend_application.user_service.service.Impl;

import com.company.BingeBox_backend_application.user_service.client.CatalogClient;
import com.company.BingeBox_backend_application.user_service.dtos.MovieResponseDto;
import com.company.BingeBox_backend_application.user_service.dtos.TvShowResponseDto;
import com.company.BingeBox_backend_application.user_service.dtos.WatchlistItemDto;
import com.company.BingeBox_backend_application.user_service.entities.UserProfile;
import com.company.BingeBox_backend_application.user_service.entities.WatchlistItem;
import com.company.BingeBox_backend_application.user_service.exceptions.ResourceNotFoundException;
import com.company.BingeBox_backend_application.user_service.repository.UserProfileRepository;
import com.company.BingeBox_backend_application.user_service.repository.WatchlistRepository;
import com.company.BingeBox_backend_application.user_service.service.WatchlistService;
import com.company.BingeBox_backend_application.user_service.auth.UserContextHolder;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WatchlistServiceImpl implements WatchlistService {

    private final WatchlistRepository watchlistRepository;
    private final UserProfileRepository userProfileRepository;
    private final CatalogClient catalogClient;

    @Override
    public WatchlistItemDto addToWatchList(Long contentId, String contentType) {
        Long userId = UserContextHolder.getCurrentUserId();
        if(userId == null) throw new RuntimeException("User not authenticated");

        UserProfile user = userProfileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        WatchlistItem item = WatchlistItem.builder()
                .contentId(contentId)
                .contentType(contentType)
                .user(user)
                .build();

        WatchlistItem saved = watchlistRepository.save(item);
        return mapToDtoWithCatalogData(saved);
    }

    @Override
    public void removeFromWatchlist(Long contentId, String contentType) {
        Long userId = UserContextHolder.getCurrentUserId();

        WatchlistItem item = watchlistRepository
                .findByUser_UserIdAndContentIdAndContentType(userId, contentId, contentType)
                .orElseThrow(() -> new RuntimeException("Watchlist item not found"));

        watchlistRepository.delete(item);
    }

    @Override
    public List<WatchlistItemDto> getUserWatchlist() {
        Long userId = UserContextHolder.getCurrentUserId();
        return watchlistRepository.findByUser_UserId(userId)
                .stream()
                .map(this::mapToDtoWithCatalogData)
                .collect(Collectors.toList());
    }

    private WatchlistItemDto mapToDtoWithCatalogData(WatchlistItem item) {
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

        return WatchlistItemDto.builder()
                .id(item.getId())
                .contentId(item.getContentId())
                .contentType(item.getContentType())
                .movie(movie)
                .tvShow(tvShow)
                .build();
    }
}
