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
}
