package com.company.BingeBox_backend_application.catalog_service.controller;

import com.company.BingeBox_backend_application.catalog_service.auth.RoleAllowed;
import com.company.BingeBox_backend_application.catalog_service.dtos.EpisodeDto;
import com.company.BingeBox_backend_application.catalog_service.service.EpisodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog/episodes")
@RequiredArgsConstructor
@Slf4j
public class EpisodeController {

    private final EpisodeService episodeService;

    @PostMapping
    @RoleAllowed({"ADMIN"})
    public ResponseEntity<EpisodeDto> createEpisode(@RequestBody EpisodeDto dto) {
        log.info("Creating new episode with title: {}", dto.getTitle());
        return ResponseEntity.ok(episodeService.createEpisode(dto));
    }

    @GetMapping("/{id}")
    @RoleAllowed({"ADMIN", "MODERATOR", "USER"})
    public ResponseEntity<EpisodeDto> getEpisodeById(@PathVariable Long id) {
        log.info("Fetching episode with id: {}", id);
        return ResponseEntity.ok(episodeService.getEpisodeById(id));
    }

    @GetMapping
    @RoleAllowed({"ADMIN", "MODERATOR", "USER"})
    public ResponseEntity<List<EpisodeDto>> getAllEpisodes() {
        log.info("Fetching all episodes");
        return ResponseEntity.ok(episodeService.getAllEpisodes());
    }

    @PutMapping("/{id}")
    @RoleAllowed({"ADMIN", "MODERATOR"})
    public ResponseEntity<EpisodeDto> updateEpisode(@PathVariable Long id, @RequestBody EpisodeDto dto) {
        log.info("Updating episode with id: {}", id);
        return ResponseEntity.ok(episodeService.updateEpisode(id, dto));
    }

    @DeleteMapping("/{id}")
    @RoleAllowed({"ADMIN"})
    public ResponseEntity<Void> deleteEpisode(@PathVariable Long id) {
        log.info("Deleting episode with id: {}", id);
        episodeService.deleteEpisode(id);
        return ResponseEntity.noContent().build();
    }
}
