package com.company.BingeBox_backend_application.catalog_service.controller;


import com.company.BingeBox_backend_application.catalog_service.dtos.MovieRequestDto;
import com.company.BingeBox_backend_application.catalog_service.dtos.MovieResponseDto;
import com.company.BingeBox_backend_application.catalog_service.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
@Slf4j
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<MovieResponseDto> createMovie(@RequestBody MovieRequestDto movieRequestDto){
        log.info("creating new movie with title: {}",movieRequestDto.getTitle());
        return ResponseEntity.ok(movieService.createMovie(movieRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponseDto> getMovieById(@PathVariable Long id) {
        log.info("Fetching movie with id: {}", id);
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @GetMapping
    public ResponseEntity<List<MovieResponseDto>> getAllMovies() {
        log.info("Fetching all movies");
        return ResponseEntity.ok(movieService.getAllMovies());
    }


    @PutMapping("/{id}")
    public ResponseEntity<MovieResponseDto> updateMovie(
            @PathVariable Long id,
            @RequestBody MovieRequestDto movieRequestDto) {
        log.info("Updating movie with id: {}", id);
        return ResponseEntity.ok(movieService.updateMovie(id, movieRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable Long id) {
        log.info("Deleting movie with id: {}", id);
        movieService.deleteMovie(id);
        return ResponseEntity.ok("Movie deleted successfully with id: " + id);
    }
}
