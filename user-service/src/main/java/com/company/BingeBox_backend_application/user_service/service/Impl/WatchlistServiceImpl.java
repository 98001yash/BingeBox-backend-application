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
    private final CatalogClient catalogClient;   // Feign client for catalog-service

    @Override
    public WatchlistItemDto addToWatchList(Long contentId, String contentType) {
        Long userId = UserContextHolder.getCurrentUserId();
        log.info("Adding contentId={} contentType={} to watchList for userId={}", contentId, contentType, userId);

        UserProfile user = userProfileRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        WatchlistItem item = WatchlistItem.builder()
                .contentId(contentId)
                .contentType(contentType)
                .user(user)
                .build();

        WatchlistItem saved = watchlistRepository.save(item);

        log.info("Successfully added contentId={} to userId={} watchlist", contentId, userId);
        return mapToDtoWithCatalogData(saved);
    }

    @Override
    public void removeFromWatchlist(Long contentId, String contentType) {
        Long userId = UserContextHolder.getCurrentUserId();
        log.info("Removing contentId={} contentType={} from watchlist for userId={}", contentId, contentType, userId);

        WatchlistItem item = watchlistRepository
                .findByUser_UserIdAndContentIdAndContentType(userId, contentId, contentType)
                .orElseThrow(() -> new ResourceNotFoundException("Watchlist item not found for contentId=" + contentId));

        watchlistRepository.delete(item);
        log.info("Removed contentId={} from userId={} watchlist", contentId, userId);
    }

    @Override
    public List<WatchlistItemDto> getUserWatchlist() {
        Long userId = UserContextHolder.getCurrentUserId();
        log.info("Fetching watchlist for userId={}", userId);

        List<WatchlistItem> items = watchlistRepository.findByUser_UserId(userId);

        return items.stream()
                .map(this::mapToDtoWithCatalogData)
                .collect(Collectors.toList());
    }

    // Map entity -> DTO + enrich with catalog data
    private WatchlistItemDto mapToDtoWithCatalogData(WatchlistItem item) {
        WatchlistItemDto.WatchlistItemDtoBuilder builder = WatchlistItemDto.builder()
                .id(item.getId())
                .contentId(item.getContentId())
                .contentType(item.getContentType());

        if ("MOVIE".equalsIgnoreCase(item.getContentType())) {
            MovieResponseDto movie = catalogClient.getMovieById(item.getContentId());
            builder.movie(movie);
        } else if ("TVSHOW".equalsIgnoreCase(item.getContentType())) {
            TvShowResponseDto tvShow = catalogClient.getTvShowById(item.getContentId());
            builder.tvShow(tvShow);
        }

        return builder.build();
    }
}
