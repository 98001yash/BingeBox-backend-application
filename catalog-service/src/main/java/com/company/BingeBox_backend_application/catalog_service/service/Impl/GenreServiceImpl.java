package com.company.BingeBox_backend_application.catalog_service.service.Impl;

import com.company.BingeBox_backend_application.catalog_service.dtos.GenreDto;
import com.company.BingeBox_backend_application.catalog_service.entity.Genre;
import com.company.BingeBox_backend_application.catalog_service.repository.GenreRepository;
import com.company.BingeBox_backend_application.catalog_service.service.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class GenreServiceImpl implements GenreService {


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
        Genre genre = genreRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Genre not found with id: "+id));
        return mapToDto(genre);
    }

    @Override
    public List<GenreDto> getAllGenres() {
        return genreRepository.findAll()
                .stream()
                .map(this:: mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public GenreDto updateGenre(Long id, GenreDto genreDto) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Genre not found with id: "+id));

        genre.setName(genreDto.getName());
        Genre updatedGenre = genreRepository.save(genre);
        return mapToDto(updatedGenre);
    }

    @Override
    public void deleteGenre(Long id) {
     Genre genre = genreRepository.findById(id)
             .orElseThrow(()->new RuntimeException("Genre not found with id: "+id));
     genreRepository.delete(genre);
    }

    public GenreDto mapToDto(Genre genre){
        return GenreDto.builder()
                .id(genre.getId())
                .name(genre.getName())
                .build();
    }
}
