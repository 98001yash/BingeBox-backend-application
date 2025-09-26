package com.company.BingeBox_backend_application.user_service.service;

import com.company.BingeBox_backend_application.user_service.dtos.WatchlistItemDto;

import java.util.List;

public interface WatchlistService {

    WatchlistItemDto addToWatchList(Long userId, Long contentId, String contentType);
    void removeFromWatchlist(Long userId, Long contentId, String contentType);

    List<WatchlistItemDto> getUserWatchlist(Long userId);
}
