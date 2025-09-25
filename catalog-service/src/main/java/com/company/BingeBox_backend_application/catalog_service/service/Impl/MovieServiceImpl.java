package com.company.BingeBox_backend_application.catalog_service.service.Impl;

import com.company.BingeBox_backend_application.catalog_service.dtos.MovieRequestDto;
import com.company.BingeBox_backend_application.catalog_service.dtos.MovieResponseDto;
import com.company.BingeBox_backend_application.catalog_service.entity.*;
import com.company.BingeBox_backend_application.catalog_service.exceptions.ResourceNotFoundException;
import com.company.BingeBox_backend_application.catalog_service.repository.*;
import com.company.BingeBox_backend_application.catalog_service.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;
    private final ProducerRepository producerRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    @Override
    public MovieResponseDto createMovie(MovieRequestDto dto) {
        Movie movie = new Movie();

        movie.setTitle(dto.getTitle());
        movie.setDescription(dto.getDescription());
        movie.setThumbnailUrl(dto.getThumbnailUrl());
        movie.setTrailerUrl(dto.getTrailerUrl());
        movie.setContentUrl(dto.getContentUrl());
        movie.setReleaseYear(dto.getReleaseYear());
        movie.setDuration(dto.getDuration());
        movie.setCast(dto.getCast());
        movie.setMaturityRating(dto.getMaturityRating());
        movie.setFeatured(dto.isFeatured());

        // Relationships
        if (dto.getGenreIds() != null) {
            Set<Genre> genres = genreRepository.findAllById(dto.getGenreIds())
                    .stream()
                    .collect(Collectors.toSet());
            movie.setGenres(genres);
        }

        if (dto.getActorIds() != null) {
            Set<Actor> actors = actorRepository.findAllById(dto.getActorIds())
                    .stream()
                    .collect(Collectors.toSet());
            movie.setActors(actors);
        }
        if (dto.getDirectorIds() != null) {
            Set<Director> directors = directorRepository.findAllById(dto.getDirectorIds())
                    .stream()
                    .collect(Collectors.toSet());
            movie.setDirectors(directors);
        }

        if (dto.getProducerIds() != null) {
            Set<Producer> producers = producerRepository
                    .findAllById(dto.getProducerIds())
                    .stream()
                    .collect(Collectors.toSet());
            movie.setProducers(producers);
        }

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + dto.getCategoryId()));
            movie.setCategory(category);
        }

        Movie savedMovie = movieRepository.save(movie);
        return convertToResponseDto(savedMovie);

    }

    @Override
    public MovieResponseDto getMovieById(Long id) {
       Movie movie = movieRepository.findById(id)
               .orElseThrow(()->new ResourceNotFoundException("Movie not found with di "+id));
       return convertToResponseDto(movie);
    }

    @Override
    public List<MovieResponseDto> getAllMovies() {
       return movieRepository.findAll().stream()
               .map(this:: convertToResponseDto)
               .collect(Collectors.toList());
    }

    @Override
    public MovieResponseDto updateMovie(Long id, MovieRequestDto dto) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id " + id));

        // Update fields
        movie.setTitle(dto.getTitle());
        movie.setDescription(dto.getDescription());
        movie.setThumbnailUrl(dto.getThumbnailUrl());
        movie.setTrailerUrl(dto.getTrailerUrl());
        movie.setContentUrl(dto.getContentUrl());
        movie.setReleaseYear(dto.getReleaseYear());
        movie.setDuration(dto.getDuration());
        movie.setCast(dto.getCast());
        movie.setMaturityRating(dto.getMaturityRating());
        movie.setFeatured(dto.isFeatured());

        // Update relationships
        if (dto.getGenreIds() != null) {
            Set<Genre> genres = genreRepository.findAllById(dto.getGenreIds()).stream().collect(Collectors.toSet());
            movie.setGenres(genres);
        }

        if (dto.getActorIds() != null) {
            Set<Actor> actors = actorRepository.findAllById(dto.getActorIds()).stream().collect(Collectors.toSet());
            movie.setActors(actors);
        }

        if (dto.getDirectorIds() != null) {
            Set<Director> directors = directorRepository.findAllById(dto.getDirectorIds()).stream().collect(Collectors.toSet());
            movie.setDirectors(directors);
        }

        if (dto.getProducerIds() != null) {
            Set<Producer> producers = producerRepository.findAllById(dto.getProducerIds()).stream().collect(Collectors.toSet());
            movie.setProducers(producers);
        }

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + dto.getCategoryId()));
            movie.setCategory(category);
        }

        Movie updatedMovie = movieRepository.save(movie);
        return convertToResponseDto(updatedMovie);
    }

    @Override
    public void deleteMovie(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id " + id));
        movieRepository.delete(movie);
    }

    @Override
    public void addActorToMovie(Long movieId, Long actorId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(()->new ResourceNotFoundException("Movie not found with id: "+movieId));

        Actor actor = actorRepository.findById(actorId)
                .orElseThrow(()->new ResourceNotFoundException("Actor not found with id "+actorId));

        movie.getActors().add(actor);
        movieRepository.save(movie);
    }

    @Override
    public void removeActorFromMovie(Long movieId, Long actorId) {
      Movie movie = movieRepository.findById(movieId)
              .orElseThrow(()->new ResourceNotFoundException("movie not found with id: "+movieId));
      Actor actor = actorRepository.findById(actorId)
              .orElseThrow(()->new ResourceNotFoundException("Actor not found with id: "+actorId));

      movie.getActors().remove(actor);
      movieRepository.save(movie);
    }

    @Override
    public void addDirectorToMovie(Long movieId, Long producerId) {

    }

    @Override
    public void removeDirectorFromMovie(Long movieId, Long directorId) {

    }

    @Override
    public void addProducerToMovie(Long movieId, Long producerId) {

    }

    @Override
    public void removeProducerFromMovie(Long movieId, Long producerId) {

    }

    @Override
    public void addGenreToMovie(Long movieId, Long genreId) {

    }

    @Override
    public void removeGenreFromMovie(Long movieId, Long genreId) {

    }

    @Override
    public void addCategoryToMovie(Long movieId, Long categoryId) {

    }

    @Override
    public void removeCategoryFromMovie(Long movieId, Long categoryId) {

    }


    private MovieResponseDto convertToResponseDto(Movie movie) {
        return modelMapper.map(movie, MovieResponseDto.class);
    }
}
