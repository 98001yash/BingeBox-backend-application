package com.company.BingeBox_backend_application.catalog_service.service.Impl;

import com.company.BingeBox_backend_application.catalog_service.dtos.MovieRequestDto;
import com.company.BingeBox_backend_application.catalog_service.dtos.MovieResponseDto;
import com.company.BingeBox_backend_application.catalog_service.entity.*;
import com.company.BingeBox_backend_application.catalog_service.exceptions.ResourceNotFoundException;
import com.company.BingeBox_backend_application.catalog_service.repository.*;
import com.company.BingeBox_backend_application.catalog_service.service.MovieService;
import com.company.BingeBox_backend_application.catalog_service.specification.MovieSpecification;
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
        // Map entity to DTO (reuse your existing mapping logic)
        return MovieResponseDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .thumbnailUrl(movie.getThumbnailUrl())
                .trailerUrl(movie.getTrailerUrl())
                .cast(movie.getCast())
                .featured(movie.isFeatured())
                .build();
    }
}
