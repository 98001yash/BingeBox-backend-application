package com.company.BingeBox_backend_application.user_service.service;

import com.company.BingeBox_backend_application.user_service.dtos.WatchlistItemDto;

import java.util.List;

public interface WatchlistService {


    WatchlistItemDto addToWatchList(Long contentId, String contentType);

    void removeFromWatchlist(Long contentId, String contentType);

    List<WatchlistItemDto> getUserWatchlist();
}
