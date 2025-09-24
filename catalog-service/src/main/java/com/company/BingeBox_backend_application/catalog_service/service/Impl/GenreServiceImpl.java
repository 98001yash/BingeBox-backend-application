package com.company.BingeBox_backend_application.catalog_service.service.Impl;

import com.company.BingeBox_backend_application.catalog_service.dtos.GenreDto;
import com.company.BingeBox_backend_application.catalog_service.service.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class GenreServiceImpl implements GenreService {


    @Override
    public GenreDto createGenre(GenreDto genreDto) {
        return null;
    }

    @Override
    public GenreDto getGenreById(Long id) {
        return null;
    }

    @Override
    public GenreDto getAllGenres() {
        return null;
    }

    @Override
    public GenreDto updateGenre(Long id, GenreDto genreDto) {
        return null;
    }

    @Override
    public void deleteGenre(Long id) {

    }
}
