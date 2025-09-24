package com.company.BingeBox_backend_application.catalog_service.service;

import com.company.BingeBox_backend_application.catalog_service.dtos.GenreDto;

public interface GenreService {

    GenreDto createGenre(GenreDto genreDto);
    GenreDto getGenreById(Long id);
    GenreDto getAllGenres();
    GenreDto updateGenre(Long id, GenreDto genreDto);
    void deleteGenre(Long id);
}
