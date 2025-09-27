package com.company.BingeBox_backend_application.catalog_service.controller;

import com.company.BingeBox_backend_application.catalog_service.auth.RoleAllowed;
import com.company.BingeBox_backend_application.catalog_service.dtos.TvShowRequestDto;
import com.company.BingeBox_backend_application.catalog_service.dtos.TvShowResponseDto;
import com.company.BingeBox_backend_application.catalog_service.service.TVShowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/catalog/TvShows")
@RequiredArgsConstructor
@Slf4j
public class TvShowController {

    private final TVShowService tvShowService;

    // --- CRUD ---

    @PostMapping
    @RoleAllowed({"ADMIN"})
    public ResponseEntity<TvShowResponseDto> createTvShow(@RequestBody TvShowRequestDto dto) {
        log.info("Creating new TV show with title: {}", dto.getTitle());
        return ResponseEntity.ok(tvShowService.createTvShow(dto));
    }

    @GetMapping("/{id}")
    @RoleAllowed({"ADMIN", "USER", "MODERATOR"})
    public ResponseEntity<TvShowResponseDto> getTvShowById(@PathVariable Long id) {
        log.info("Fetching TV show with id: {}", id);
        return ResponseEntity.ok(tvShowService.getTvShowById(id));
    }

    @GetMapping
    @RoleAllowed({"ADMIN", "USER", "MODERATOR"})
    public ResponseEntity<Set<TvShowResponseDto>> getAllTvShows() {
        log.info("Fetching all TV shows");
        return ResponseEntity.ok((Set<TvShowResponseDto>) tvShowService.getAllTvShows());
    }

    @PutMapping("/{id}")
    @RoleAllowed({"ADMIN", "MODERATOR"})
    public ResponseEntity<TvShowResponseDto> updateTvShow(
            @PathVariable Long id,
            @RequestBody TvShowRequestDto dto) {
        log.info("Updating TV show with id: {}", id);
        return ResponseEntity.ok(tvShowService.updateTvShow(id, dto));
    }

    @DeleteMapping("/{id}")
    @RoleAllowed({"ADMIN"})
    public ResponseEntity<String> deleteTvShow(@PathVariable Long id) {
        log.info("Deleting TV show with id: {}", id);
        tvShowService.deleteTvShow(id);
        return ResponseEntity.ok("TV show deleted successfully with id: " + id);
    }

    // --- Genre ---
    @PutMapping("/{tvShowId}/genres/{genreId}")
    @RoleAllowed({"ADMIN", "MODERATOR"})
    public ResponseEntity<TvShowResponseDto> addGenreToTvShow(@PathVariable Long tvShowId,
                                                              @PathVariable Long genreId) {
        return ResponseEntity.ok(tvShowService.addGenreToTvShow(tvShowId, genreId));
    }

    @DeleteMapping("/{tvShowId}/genres/{genreId}")
    @RoleAllowed({"ADMIN", "MODERATOR"})
    public ResponseEntity<TvShowResponseDto> removeGenreFromTvShow(@PathVariable Long tvShowId,
                                                                   @PathVariable Long genreId) {
        return ResponseEntity.ok(tvShowService.removeGenreFromTvShow(tvShowId, genreId));
    }

    // --- Actor ---
    @PutMapping("/{tvShowId}/actors/{actorId}")
    @RoleAllowed({"ADMIN", "MODERATOR"})
    public ResponseEntity<TvShowResponseDto> addActorToTvShow(@PathVariable Long tvShowId,
                                                              @PathVariable Long actorId) {
        return ResponseEntity.ok(tvShowService.addActorToTvShow(tvShowId, actorId));
    }

    @DeleteMapping("/{tvShowId}/actors/{actorId}")
    @RoleAllowed({"ADMIN", "MODERATOR"})
    public ResponseEntity<TvShowResponseDto> removeActorFromTvShow(@PathVariable Long tvShowId,
                                                                   @PathVariable Long actorId) {
        return ResponseEntity.ok(tvShowService.removeActorFromTvShow(tvShowId, actorId));
    }

    // --- Director ---
    @PutMapping("/{tvShowId}/directors/{directorId}")
    @RoleAllowed({"ADMIN", "MODERATOR"})
    public ResponseEntity<TvShowResponseDto> addDirectorToTvShow(@PathVariable Long tvShowId,
                                                                 @PathVariable Long directorId) {
        return ResponseEntity.ok(tvShowService.addDirectorToTvShow(tvShowId, directorId));
    }

    @DeleteMapping("/{tvShowId}/directors/{directorId}")
    @RoleAllowed({"ADMIN", "MODERATOR"})
    public ResponseEntity<TvShowResponseDto> removeDirectorFromTvShow(@PathVariable Long tvShowId,
                                                                      @PathVariable Long directorId) {
        return ResponseEntity.ok(tvShowService.removeDirectorFromTvShow(tvShowId, directorId));
    }

    // --- Producer ---
    @PutMapping("/{tvShowId}/producers/{producerId}")
    @RoleAllowed({"ADMIN"})
    public ResponseEntity<TvShowResponseDto> addProducerToTvShow(@PathVariable Long tvShowId,
                                                                 @PathVariable Long producerId) {
        return ResponseEntity.ok(tvShowService.addProducerToTvShow(tvShowId, producerId));
    }

    @DeleteMapping("/{tvShowId}/producers/{producerId}")
    @RoleAllowed({"ADMIN"})
    public ResponseEntity<TvShowResponseDto> removeProducerFromTvShow(@PathVariable Long tvShowId,
                                                                      @PathVariable Long producerId) {
        return ResponseEntity.ok(tvShowService.removeProducerFromTvShow(tvShowId, producerId));
    }

    // --- Category ---
    @PutMapping("/{tvShowId}/category/{categoryId}")
    @RoleAllowed({"ADMIN", "MODERATOR"})
    public ResponseEntity<TvShowResponseDto> addCategoryToTvShow(@PathVariable Long tvShowId,
                                                                 @PathVariable Long categoryId) {
        return ResponseEntity.ok(tvShowService.addCategoryToTvShow(tvShowId, categoryId));
    }

    @DeleteMapping("/{tvShowId}/category")
    @RoleAllowed({"ADMIN", "MODERATOR"})
    public ResponseEntity<TvShowResponseDto> removeCategoryFromTvShow(@PathVariable Long tvShowId) {
        return ResponseEntity.ok(tvShowService.removeCategoryFromTvShow(tvShowId));
    }

    // --- Search and filter ---
    @GetMapping("/search")
    @RoleAllowed({"ADMIN", "USER", "MODERATOR"})
    public Page<TvShowResponseDto> searchTvShows(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Set<Long> genreIds,
            @RequestParam(required = false) Set<Long> actorIds,
            @RequestParam(required = false) Set<Long> directorIds,
            @RequestParam(required = false) Set<Long> producerIds,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Boolean featured,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return tvShowService.searchTvShows(title, genreIds, actorIds, directorIds, producerIds, categoryId, featured, pageable);
    }
}
