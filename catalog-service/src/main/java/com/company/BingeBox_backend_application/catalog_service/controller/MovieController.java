package com.company.BingeBox_backend_application.catalog_service.controller;


import com.company.BingeBox_backend_application.catalog_service.dtos.MovieRequestDto;
import com.company.BingeBox_backend_application.catalog_service.dtos.MovieResponseDto;
import com.company.BingeBox_backend_application.catalog_service.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/catalog/movies")
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

                //======Actor relations=====//

    @PostMapping("/{movieId}/actor/{actorId}")
    public ResponseEntity<Void> addActor(@PathVariable Long movieId, @PathVariable Long actorId){
        movieService.addActorToMovie(movieId, actorId);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{movieId}/actors/{actorId}")
    public ResponseEntity<Void> removeActor(@PathVariable Long movieId, @PathVariable Long actorId){
        movieService.removeActorFromMovie(movieId, actorId);
        return ResponseEntity.noContent().build();
    }


    //======Director relation==========//
    @PostMapping("/{movieId}/directors/{directorId}")
    public ResponseEntity<Void> addDirector(@PathVariable Long movieId, @PathVariable Long directorId){
        movieService.addDirectorToMovie(movieId, directorId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{movieId}/directors/{directorId}")
    public ResponseEntity<Void> removeDirector(@PathVariable Long movieId, @PathVariable Long directorId){
        movieService.removeDirectorFromMovie(movieId, directorId);
        return ResponseEntity.ok().build();
    }

    //============Producer relations ================//

    @PostMapping("/{movieId}/producers/{producerId}")
    public ResponseEntity<Void> addProducer(@PathVariable Long movieId, @PathVariable Long producerId) {
        movieService.addProducerToMovie(movieId, producerId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{movieId}/producers/{producerId}")
    public ResponseEntity<Void> removeProducer(@PathVariable Long movieId, @PathVariable Long producerId) {
        movieService.removeProducerFromMovie(movieId, producerId);
        return ResponseEntity.noContent().build();
    }


    // --- Genre relations ---

    @PostMapping("/{movieId}/genres/{genreId}")
    public ResponseEntity<Void> addGenre(@PathVariable Long movieId, @PathVariable Long genreId) {
        movieService.addGenreToMovie(movieId, genreId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{movieId}/genres/{genreId}")
    public ResponseEntity<Void> removeGenre(@PathVariable Long movieId, @PathVariable Long genreId) {
        movieService.removeGenreFromMovie(movieId, genreId);
        return ResponseEntity.noContent().build();
    }

    // --- Category relations ---

    @PostMapping("/{movieId}/categories/{categoryId}")
    public ResponseEntity<Void> addCategory(@PathVariable Long movieId, @PathVariable Long categoryId) {
        movieService.addCategoryToMovie(movieId, categoryId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{movieId}/categories/{categoryId}")
    public ResponseEntity<Void> removeCategory(@PathVariable Long movieId, @PathVariable Long categoryId) {
        movieService.removeCategoryFromMovie(movieId, categoryId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/search")
    public List<MovieResponseDto> searchMovies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Set<Long> genreIds,
            @RequestParam(required = false) Set<Long> actorIds,
            @RequestParam(required = false) Set<Long> directorIds,
            @RequestParam(required = false) Set<Long> producerIds,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Boolean featured
    ) {
        return movieService.searchMovies(title, genreIds, actorIds, directorIds, producerIds, categoryId, featured);
    }
}
