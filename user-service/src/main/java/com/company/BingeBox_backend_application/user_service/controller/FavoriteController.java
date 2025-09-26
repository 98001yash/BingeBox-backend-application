package com.company.BingeBox_backend_application.user_service.controller;

import com.company.BingeBox_backend_application.user_service.dtos.FavoriteItemDto;
import com.company.BingeBox_backend_application.user_service.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/favorites")
@RequiredArgsConstructor
@Slf4j
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping
    public ResponseEntity<FavoriteItemDto> addToFavorites(
            @PathVariable Long userId,
            @RequestParam Long contentId,
            @RequestParam String contentType) {
        log.info("API Request: Add to favorites userId={}, contentId={}, contentType={}", userId, contentId, contentType);
        FavoriteItemDto item = favoriteService.addToFavorite(userId, contentId, contentType);
        return ResponseEntity.ok(item);
    }

    @DeleteMapping
    public ResponseEntity<Void> removeFromFavorites(
            @PathVariable Long userId,
            @RequestParam Long contentId,
            @RequestParam String contentType) {
        log.info("API Request: Remove from favorites userId={}, contentId={}, contentType={}", userId, contentId, contentType);
        favoriteService.removeFromFavorites(userId, contentId, contentType);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<FavoriteItemDto>> getUserFavorites(@PathVariable Long userId) {
        log.info("API Request: Get favorites for userId={}", userId);
        List<FavoriteItemDto> favorites = favoriteService.getUserFavorites(userId);
        return ResponseEntity.ok(favorites);
    }
}