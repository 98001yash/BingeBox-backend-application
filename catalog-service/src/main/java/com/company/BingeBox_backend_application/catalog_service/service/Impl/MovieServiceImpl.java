package com.company.BingeBox_backend_application.catalog_service.service.Impl;

import com.company.BingeBox_backend_application.catalog_service.dtos.*;
import com.company.BingeBox_backend_application.catalog_service.entity.*;
import com.company.BingeBox_backend_application.catalog_service.exceptions.ResourceNotFoundException;
import com.company.BingeBox_backend_application.catalog_service.repository.*;
import com.company.BingeBox_backend_application.catalog_service.service.MovieService;
import com.company.BingeBox_backend_application.catalog_service.specification.MovieSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
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

    public MovieResponseDto getMovieById(Long id) {
        // Fetch the movie entity
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));

        // Defensive mapping to DTO
        MovieResponseDto dto = new MovieResponseDto();

        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setDescription(movie.getDescription());
        dto.setThumbnailUrl(movie.getThumbnailUrl());
        dto.setTrailerUrl(movie.getTrailerUrl());
        dto.setContentUrl(movie.getContentUrl());
        dto.setReleaseYear(movie.getReleaseYear());
        dto.setDuration(movie.getDuration());
        dto.setMaturityRating(movie.getMaturityRating());
        dto.setFeatured(movie.isFeatured());

        // Collections mapping with null-safety
        dto.setGenres(movie.getGenres() != null
                ? movie.getGenres().stream()
                .filter(Objects::nonNull)
                .map(g -> new GenreDto(g.getId(), g.getName()))
                .collect(Collectors.toSet())
                : Set.of());

        dto.setCast(movie.getCast() != null ? movie.getCast() : List.of());

        dto.setActors(movie.getActors() != null
                ? movie.getActors().stream()
                .filter(Objects::nonNull)
                .map(a -> new ActorDto(a.getId(), a.getName(), a.getProfileImageUrl()))
                .collect(Collectors.toSet())
                : Set.of());

        dto.setDirectors(movie.getDirectors() != null
                ? movie.getDirectors().stream()
                .filter(Objects::nonNull)
                .map(d -> new DirectorDto(d.getId(), d.getName(), d.getProfileImageUrl()))
                .collect(Collectors.toSet())
                : Set.of());

        dto.setProducers(movie.getProducers() != null
                ? movie.getProducers().stream()
                .filter(Objects::nonNull)
                .map(p -> new ProducerDto(p.getId(), p.getName(), p.getProfileImageUrl()))
                .collect(Collectors.toSet())
                : Set.of());

        dto.setCategory(movie.getCategory() != null
                ? new CategoryDto(movie.getCategory().getId(), movie.getCategory().getName())
                : null);

        return dto;
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
    public void addDirectorToMovie(Long movieId, Long directorId) {
       Movie movie = movieRepository.findById(movieId)
               .orElseThrow(()->new ResourceNotFoundException("Movie not found with id: "+movieId));

       Director director = directorRepository.findById(directorId)
               .orElseThrow(()->new ResourceNotFoundException("Director not found with id: "+directorId));

       movie.getDirectors().add(director);
       movieRepository.save(movie);
    }

    @Override
    public void removeDirectorFromMovie(Long movieId, Long directorId) {
      Movie movie = movieRepository.findById(movieId)
              .orElseThrow(()->new ResourceNotFoundException("Movie not found with id: "+movieId));

      Director director = directorRepository.findById(directorId)
              .orElseThrow(()->new ResourceNotFoundException("Director not found with id "+directorId));

      movie.getDirectors().remove(director);
      movieRepository.save(movie);
    }

    @Override
    public void addProducerToMovie(Long movieId, Long producerId) {
      Movie movie = movieRepository.findById(movieId)
              .orElseThrow(()->new ResourceNotFoundException("Movie not found with id: "+movieId));

      Producer producer = producerRepository.findById(producerId)
              .orElseThrow(()->new ResourceNotFoundException("Producer not found with id: "+producerId));

      movie.getProducers().add(producer);
      movieRepository.save(movie);
    }

    @Override
    public void removeProducerFromMovie(Long movieId, Long producerId) {
       Movie movie = movieRepository.findById(movieId)
               .orElseThrow(()->new ResourceNotFoundException("Movie not found with id: "+movieId));

       Producer producer = producerRepository.findById(producerId)
               .orElseThrow(()->new ResourceNotFoundException("Producer not found with id: "+producerId));

       movie.getProducers().remove(producer);
       movieRepository.save(movie);
    }

    @Override
    public void addGenreToMovie(Long movieId, Long genreId) {
      Movie movie = movieRepository.findById(movieId)
              .orElseThrow(()->new ResourceNotFoundException("Movie not found with id: "+movieId));
      Genre genre = genreRepository.findById(genreId)
              .orElseThrow(()->new ResourceNotFoundException("Genre not found with id: "+genreId));

      movie.getGenres().add(genre);
      movieRepository.save(movie);
    }

    @Override
    public void removeGenreFromMovie(Long movieId, Long genreId) {
      Movie movie = movieRepository.findById(movieId)
              .orElseThrow(()->new ResourceNotFoundException("Movie not found with id "+movieId));
      Genre genre = genreRepository.findById(genreId)
              .orElseThrow(()->new ResourceNotFoundException("Genre not found with id: "+genreId));

      movie.getGenres().remove(genre);
      movieRepository.save(movie);
    }

    @Override
    public void addCategoryToMovie(Long movieId, Long categoryId) {
       Movie movie = movieRepository.findById(movieId)
               .orElseThrow(()->new ResourceNotFoundException("Movie not found with id "+movieId));

       Category category = categoryRepository.findById(categoryId)
               .orElseThrow(()->new ResourceNotFoundException("Category not found with id: "+categoryId));

       movie.setCategory(category);
       movieRepository.save(movie);
    }

    @Override
    public void removeCategoryFromMovie(Long movieId, Long categoryId) {
     Movie movie = movieRepository.findById(movieId)
             .orElseThrow(()-> new ResourceNotFoundException("Movie not found with id "+movieId));

     movie.setCategory(null);
     movieRepository.save(movie);
    }

    @Override
    public List<MovieResponseDto> searchMovies(String title, Set<Long> genreIds, Set<Long> actorIds,
                                               Set<Long> directorIds, Set<Long> producerIds,
                                               Long categoryId, Boolean featured) {

        List<Movie> movies = movieRepository.findAll(
                MovieSpecification.filterBy(title, genreIds, actorIds, directorIds, producerIds, categoryId, featured)
        );

        return movies.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    private MovieResponseDto convertToResponseDto(Movie movie) {
        return modelMapper.map(movie, MovieResponseDto.class);
    }


    private MovieResponseDto mapToResponse(Movie movie) {
        return MovieResponseDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .thumbnailUrl(movie.getThumbnailUrl())
                .trailerUrl(movie.getTrailerUrl())
                .contentUrl(movie.getContentUrl())
                .releaseYear(movie.getReleaseYear())
                .duration(movie.getDuration())
                .cast(movie.getCast() != null ? movie.getCast() : List.of())
                .featured(movie.isFeatured())
                .genres(movie.getGenres() != null ? movie.getGenres().stream()
                        .map(g -> new GenreDto(g.getId(), g.getName()))
                        .collect(Collectors.toSet()) : Set.of())
                .actors(movie.getActors() != null ? movie.getActors().stream()
                        .map(a -> new ActorDto(a.getId(), a.getName(), a.getProfileImageUrl()))
                        .collect(Collectors.toSet()) : Set.of())
                .directors(movie.getDirectors() != null ? movie.getDirectors().stream()
                        .map(d -> new DirectorDto(d.getId(), d.getName(), d.getProfileImageUrl()))
                        .collect(Collectors.toSet()) : Set.of())
                .producers(movie.getProducers() != null ? movie.getProducers().stream()
                        .map(p -> new ProducerDto(p.getId(), p.getName(), p.getProfileImageUrl()))
                        .collect(Collectors.toSet()) : Set.of())
                .category(movie.getCategory() != null ?
                        new CategoryDto(movie.getCategory().getId(), movie.getCategory().getName()) : null)
                .maturityRating(movie.getMaturityRating())
                .build();
    }



}
