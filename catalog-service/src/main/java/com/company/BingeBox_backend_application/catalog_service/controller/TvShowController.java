package com.company.BingeBox_backend_application.catalog_service.controller;

import com.company.BingeBox_backend_application.catalog_service.dtos.TvShowRequestDto;
import com.company.BingeBox_backend_application.catalog_service.dtos.TvShowResponseDto;
import com.company.BingeBox_backend_application.catalog_service.service.TVShowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog/TvShows")
@RequiredArgsConstructor
@Slf4j
public class TvShowController {

    private final TVShowService tvShowService;

    @PostMapping
    public ResponseEntity<TvShowResponseDto> createTvShow(@RequestBody TvShowRequestDto dto) {
        log.info("Creating new TV show with title: {}", dto.getTitle());
        return ResponseEntity.ok(tvShowService.createTvShow(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TvShowResponseDto> getTvShowById(@PathVariable Long id) {
        log.info("Fetching TV show with id: {}", id);
        return ResponseEntity.ok(tvShowService.getTvShowById(id));
    }

    @GetMapping
    public ResponseEntity<List<TvShowResponseDto>> getAllTvShows() {
        log.info("Fetching all TV shows");
        return ResponseEntity.ok(tvShowService.getAllTvShows());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TvShowResponseDto> updateTvShow(
            @PathVariable Long id,
            @RequestBody TvShowRequestDto dto) {
        log.info("Updating TV show with id: {}", id);
        return ResponseEntity.ok(tvShowService.updateTvShow(id, dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTvShow(@PathVariable Long id) {
        log.info("Deleting TV show with id: {}", id);
        tvShowService.deleteTvShow(id);
        return ResponseEntity.ok("TV show deleted successfully with id: " + id);
    }
}
