package com.company.BingeBox_backend_application.user_service.controller;

import com.company.BingeBox_backend_application.user_service.dtos.WatchlistItemDto;
import com.company.BingeBox_backend_application.user_service.service.WatchlistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/watchlist")
@RequiredArgsConstructor
@Slf4j
public class WatchlistController {

    private final WatchlistService watchlistService;

    @PostMapping
    public ResponseEntity<WatchlistItemDto> addToWatchlist(
            @PathVariable Long userId,
            @RequestParam Long contentId,
            @RequestParam String contentType) {
        log.info("API Request: Add to watchlist userId={}, contentId={}, contentType={}", userId, contentId, contentType);
        WatchlistItemDto item = watchlistService.addToWatchList(userId, contentId, contentType);
        return ResponseEntity.ok(item);
    }

    @DeleteMapping
    public ResponseEntity<Void> removeFromWatchlist(
            @PathVariable Long userId,
            @RequestParam Long contentId,
            @RequestParam String contentType) {
        log.info("API Request: Remove from watchlist userId={}, contentId={}, contentType={}", userId, contentId, contentType);
        watchlistService.removeFromWatchlist(userId, contentId, contentType);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<WatchlistItemDto>> getUserWatchlist(@PathVariable Long userId) {
        log.info("API Request: Get watchlist for userId={}", userId);
        List<WatchlistItemDto> watchlist = watchlistService.getUserWatchlist(userId);
        return ResponseEntity.ok(watchlist);
    }
}