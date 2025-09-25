package com.company.BingeBox_backend_application.catalog_service.service;

import com.company.BingeBox_backend_application.catalog_service.dtos.MovieRequestDto;
import com.company.BingeBox_backend_application.catalog_service.dtos.MovieResponseDto;

import java.util.List;

public interface MovieService {

    MovieResponseDto createMovie(MovieRequestDto movieRequestDto);

    MovieResponseDto getMovieById(Long id);

    List<MovieResponseDto> getAllMovies();

    MovieResponseDto updateMovie(Long id, MovieRequestDto movieRequestDto);

    void deleteMovie(Long id);

    void addActorToMovie(Long movieId, Long actorId);
    void removeActorFromMovie(Long movieId, Long actorId);


    void addDirectorToMovie(Long movieId, Long directorId);
    void removeDirectorFromMovie(Long movieId, Long directorId);

    void addProducerToMovie(Long movieId, Long producerId);
    void removeProducerFromMovie(Long movieId, Long producerId);

    void addGenreToMovie(Long movieId, Long genreId);
    void removeGenreFromMovie(Long movieId, Long genreId);

    void addCategoryToMovie(Long movieId, Long categoryId);
    void removeCategoryFromMovie(Long movieId, Long categoryId);
}
