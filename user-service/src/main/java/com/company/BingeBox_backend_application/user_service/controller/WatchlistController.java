package com.company.BingeBox_backend_application.user_service.controller;

import com.company.BingeBox_backend_application.user_service.auth.RoleAllowed;
import com.company.BingeBox_backend_application.user_service.dtos.WatchlistItemDto;
import com.company.BingeBox_backend_application.user_service.service.WatchlistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/watchlist")
@RequiredArgsConstructor
@Slf4j
public class WatchlistController {

    private final WatchlistService watchlistService;

    @PostMapping
    @RoleAllowed({"USER"})  // Only users can add
    public ResponseEntity<WatchlistItemDto> addToWatchlist(
            @RequestParam Long contentId,
            @RequestParam String contentType) {
        log.info("API Request: Add to watchlist contentId={}, contentType={}", contentId, contentType);
        WatchlistItemDto item = watchlistService.addToWatchList(contentId, contentType);
        return ResponseEntity.ok(item);
    }

    @DeleteMapping
    @RoleAllowed({"USER"})  // Only users can remove
    public ResponseEntity<Void> removeFromWatchlist(
            @RequestParam Long contentId,
            @RequestParam String contentType) {
        log.info("API Request: Remove from watchlist contentId={}, contentType={}", contentId, contentType);
        watchlistService.removeFromWatchlist(contentId, contentType);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @RoleAllowed({"USER"})  // Only users can get their watchlist
    public ResponseEntity<List<WatchlistItemDto>> getUserWatchlist() {
        log.info("API Request: Get watchlist for current user");
        List<WatchlistItemDto> watchlist = watchlistService.getUserWatchlist();
        return ResponseEntity.ok(watchlist);
    }
}