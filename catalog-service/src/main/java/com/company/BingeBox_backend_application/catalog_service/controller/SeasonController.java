package com.company.BingeBox_backend_application.catalog_service.controller;

import com.company.BingeBox_backend_application.catalog_service.auth.RoleAllowed;
import com.company.BingeBox_backend_application.catalog_service.dtos.SeasonDto;
import com.company.BingeBox_backend_application.catalog_service.service.SeasonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog/seasons")
@RequiredArgsConstructor
@Slf4j
public class SeasonController {

    private final SeasonService seasonService;

    @PostMapping
    @RoleAllowed({"ADMIN"})
    public ResponseEntity<SeasonDto> createSeason(@RequestBody SeasonDto dto) {
        log.info("Creating new season for TV Show id: {}", dto.getTvShowId());
        return ResponseEntity.ok(seasonService.createSeason(dto));
    }

    @GetMapping("/{id}")
    @RoleAllowed({"ADMIN", "MODERATOR", "USER"})
    public ResponseEntity<SeasonDto> getSeasonById(@PathVariable Long id) {
        log.info("Fetching season with id: {}", id);
        return ResponseEntity.ok(seasonService.getSeasonById(id));
    }

    @GetMapping
    @RoleAllowed({"ADMIN", "MODERATOR", "USER"})
    public ResponseEntity<List<SeasonDto>> getAllSeasons() {
        log.info("Fetching all seasons");
        return ResponseEntity.ok(seasonService.getAllSeasons());
    }

    @PutMapping("/{id}")
    @RoleAllowed({"ADMIN", "MODERATOR"})
    public ResponseEntity<SeasonDto> updateSeason(@PathVariable Long id, @RequestBody SeasonDto dto) {
        log.info("Updating season with id: {}", id);
        return ResponseEntity.ok(seasonService.updateSeason(id, dto));
    }

    @DeleteMapping("/{id}")
    @RoleAllowed({"ADMIN"})
    public ResponseEntity<Void> deleteSeason(@PathVariable Long id) {
        log.info("Deleting season with id: {}", id);
        seasonService.deleteSeason(id);
        return ResponseEntity.noContent().build();
    }
}
