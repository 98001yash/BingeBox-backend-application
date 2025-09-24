package com.company.BingeBox_backend_application.catalog_service.service.Impl;

import com.company.BingeBox_backend_application.catalog_service.dtos.GenreDto;
import com.company.BingeBox_backend_application.catalog_service.entity.Genre;
import com.company.BingeBox_backend_application.catalog_service.repository.GenreRepository;
import com.company.BingeBox_backend_application.catalog_service.service.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class GenreServiceImpl implements GenreService {


    private final GenreService genreService;
    private final GenreRepository genreRepository;


    @Override
    public GenreDto createGenre(GenreDto genreDto) {
        if (genreRepository.existsByName(genreDto.getName())) {
            throw new RuntimeException("Genre already exists with name: " + genreDto.getName());
        }
        Genre genre = Genre.builder()
                .name(genreDto.getName())
                .build();
        Genre savedGenre = genreRepository.save(genre);
        return mapToDto(savedGenre);
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
