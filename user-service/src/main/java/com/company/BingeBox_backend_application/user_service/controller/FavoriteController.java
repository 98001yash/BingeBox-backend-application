package com.company.BingeBox_backend_application.user_service.controller;

import com.company.BingeBox_backend_application.user_service.auth.RoleAllowed;
import com.company.BingeBox_backend_application.user_service.dtos.FavoriteItemDto;
import com.company.BingeBox_backend_application.user_service.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/favorites")
@RequiredArgsConstructor
@Slf4j
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping
    @RoleAllowed({"USER"})  // Only users can add to favorites
    public ResponseEntity<FavoriteItemDto> addToFavorites(
            @RequestParam Long contentId,
            @RequestParam String contentType) {
        log.info("API Request: Add to favorites contentId={}, contentType={}", contentId, contentType);
        FavoriteItemDto item = favoriteService.addToFavorite(contentId, contentType);
        return ResponseEntity.ok(item);
    }

    @DeleteMapping
    @RoleAllowed({"USER"})  // Only users can remove from favorites
    public ResponseEntity<Void> removeFromFavorites(
            @RequestParam Long contentId,
            @RequestParam String contentType) {
        log.info("API Request: Remove from favorites contentId={}, contentType={}", contentId, contentType);
        favoriteService.removeFromFavorite(contentId, contentType);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @RoleAllowed({"USER"})  // Only users can view their favorites
    public ResponseEntity<List<FavoriteItemDto>> getUserFavorites() {
        log.info("API Request: Get favorites for current user");
        List<FavoriteItemDto> favorites = favoriteService.getUserFavorites();
        return ResponseEntity.ok(favorites);
    }
}
