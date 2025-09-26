package com.company.BingeBox_backend_application.user_service.service;

import com.company.BingeBox_backend_application.user_service.dtos.FavoriteItemDto;

import java.util.List;

public interface FavoriteService {

    FavoriteItemDto addToFavorite(Long userId, Long contentId, String contentType);
    void removeFromFavorites(Long userId, Long contentId, String contentType);

    List<FavoriteItemDto> getUserFavorites(Long userId);
}
