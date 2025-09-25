package com.company.BingeBox_backend_application.catalog_service.service.Impl;

import com.company.BingeBox_backend_application.catalog_service.dtos.*;
import com.company.BingeBox_backend_application.catalog_service.entity.Genre;
import com.company.BingeBox_backend_application.catalog_service.entity.TVShow;
import com.company.BingeBox_backend_application.catalog_service.exceptions.ResourceNotFoundException;
import com.company.BingeBox_backend_application.catalog_service.repository.*;
import com.company.BingeBox_backend_application.catalog_service.service.TVShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TvShowServiceImpl implements TVShowService {

    private final TVShowRepository tvShowRepository;
    private final GenreRepository genreRepository;
    private final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;
    private final ProducerRepository producerRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public TvShowResponseDto createTvShow(TvShowRequestDto dto) {
        TVShow tvShow = mapToEntity(dto);
        return mapToResponse(tvShowRepository.save(tvShow));
    }

    @Override
    public TvShowResponseDto getTvShowById(Long id) {
        TVShow tvShow = tvShowRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TV Show not found with id: " + id));
        return mapToResponse(tvShow);
    }

    @Override
    public List<TvShowResponseDto> getAllTvShows() {
        return tvShowRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TvShowResponseDto updateTvShow(Long id, TvShowRequestDto dto) {
        TVShow tvShow = tvShowRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TV Show not found with id: " + id));

        tvShow.setTitle(dto.getTitle());
        tvShow.setDescription(dto.getDescription());
        tvShow.setThumbnailUrl(dto.getThumbnailUrl());
        tvShow.setTrailerUrl(dto.getTrailerUrl());
        tvShow.setCast(dto.getCast());
        tvShow.setMaturityRating(dto.getMaturityRating());
        tvShow.setFeatured(dto.isFeatured());

        // relations
        if (dto.getGenreIds() != null) {
            tvShow.setGenres(genreRepository.findAllById(dto.getGenreIds()).stream().collect(Collectors.toSet()));
        }
        if (dto.getActorIds() != null) {
            tvShow.setActors(actorRepository.findAllById(dto.getActorIds()).stream().collect(Collectors.toSet()));
        }
        if (dto.getDirectorIds() != null) {
            tvShow.setDirectors(directorRepository.findAllById(dto.getDirectorIds()).stream().collect(Collectors.toSet()));
        }
        if (dto.getProducerIds() != null) {
            tvShow.setProducers(producerRepository.findAllById(dto.getProducerIds()).stream().collect(Collectors.toSet()));
        }
        if (dto.getCategoryId() != null) {
            tvShow.setCategory(categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + dto.getCategoryId())));
        }

        return mapToResponse(tvShowRepository.save(tvShow));
    }

    @Override
    public void deleteTvShow(Long id) {
        if (!tvShowRepository.existsById(id)) {
            throw new RuntimeException("TV Show not found with id: " + id);
        }
        tvShowRepository.deleteById(id);
    }

    @Override
    public TvShowResponseDto addGenreToTvShow(Long tvShowId, Long genreId) {
        TVShow tvShow = tvShowRepository.findById(tvShowId)
                .orElseThrow(()->new ResourceNotFoundException("TV show not found"));
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(()->new ResourceNotFoundException("Genre not found"));

        tvShow.getGenres().add(genre);
        return mapToResponse(tvShowRepository.save(tvShow));
    }

    @Override
    public TvShowResponseDto removeGenreFromTvShow(Long tvShowId, Long genreId) {
        TVShow tvShow = tvShowRepository.findById(tvShowId)
                .orElseThrow(() -> new ResourceNotFoundException("TV show not found"));
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found"));

        tvShow.getGenres().remove(genre);
        return mapToResponse(tvShowRepository.save(tvShow));
    }

    @Override
    public TvShowResponseDto addActorToTvShow(Long tvShowId, Long actorId) {
        TVShow tvShow = tvShowRepository.findById(tvShowId)
                .orElseThrow(() -> new ResourceNotFoundException("TV show not found"));
        var actor = actorRepository.findById(actorId)
                .orElseThrow(() -> new ResourceNotFoundException("Actor not found"));

        tvShow.getActors().add(actor);
        return mapToResponse(tvShowRepository.save(tvShow));
    }

    @Override
    public TvShowResponseDto removeActorFromTvShow(Long tvShowId, Long actorId) {
        TVShow tvShow = tvShowRepository.findById(tvShowId)
                .orElseThrow(() -> new ResourceNotFoundException("TV show not found"));
        var actor = actorRepository.findById(actorId)
                .orElseThrow(() -> new ResourceNotFoundException("Actor not found"));

        tvShow.getActors().remove(actor);
        return mapToResponse(tvShowRepository.save(tvShow));
    }

    @Override
    public TvShowResponseDto addDirectorToTvShow(Long tvShowId, Long directorId) {
        TVShow tvShow = tvShowRepository.findById(tvShowId)
                .orElseThrow(() -> new ResourceNotFoundException("TV show not found"));
        var director = directorRepository.findById(directorId)
                .orElseThrow(() -> new ResourceNotFoundException("Director not found"));

        tvShow.getDirectors().add(director);
        return mapToResponse(tvShowRepository.save(tvShow));
    }

    @Override
    public TvShowResponseDto removeDirectorFromTvShow(Long tvShowId, Long directorId) {
        TVShow tvShow = tvShowRepository.findById(tvShowId)
                .orElseThrow(() -> new ResourceNotFoundException("TV show not found"));
        var director = directorRepository.findById(directorId)
                .orElseThrow(() -> new ResourceNotFoundException("Director not found"));

        tvShow.getDirectors().remove(director);
        return mapToResponse(tvShowRepository.save(tvShow));
    }

    @Override
    public TvShowResponseDto addProducerToTvShow(Long tvShowId, Long producerId) {
        TVShow tvShow = tvShowRepository.findById(tvShowId)
                .orElseThrow(() -> new ResourceNotFoundException("TV show not found"));
        var producer = producerRepository.findById(producerId)
                .orElseThrow(() -> new ResourceNotFoundException("Producer not found"));

        tvShow.getProducers().add(producer);
        return mapToResponse(tvShowRepository.save(tvShow));
    }

    @Override
    public TvShowResponseDto removeProducerFromTvShow(Long tvShowId, Long producerId) {
        TVShow tvShow = tvShowRepository.findById(tvShowId)
                .orElseThrow(() -> new ResourceNotFoundException("TV show not found"));
        var producer = producerRepository.findById(producerId)
                .orElseThrow(() -> new ResourceNotFoundException("Producer not found"));

        tvShow.getProducers().remove(producer);
        return mapToResponse(tvShowRepository.save(tvShow));
    }

    @Override
    public TvShowResponseDto addCategoryToTvShow(Long tvShowId, Long categoryId) {
        TVShow tvShow = tvShowRepository.findById(tvShowId)
                .orElseThrow(() -> new ResourceNotFoundException("TV show not found with id: " + tvShowId));

        var category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));

        tvShow.setCategory(category); // just set the category
        return mapToResponse(tvShowRepository.save(tvShow));
    }

    @Override
    public TvShowResponseDto removeCategoryFromTvShow(Long tvShowId) {
        TVShow tvShow = tvShowRepository.findById(tvShowId)
                .orElseThrow(() -> new ResourceNotFoundException("TV show not found with id: " + tvShowId));

        tvShow.setCategory(null); // remove the category
        return mapToResponse(tvShowRepository.save(tvShow));
    }

    // --- mapping helpers ---

    private TVShow mapToEntity(TvShowRequestDto dto) {
        TVShow tvShow = new TVShow();
        tvShow.setTitle(dto.getTitle());
        tvShow.setDescription(dto.getDescription());
        tvShow.setThumbnailUrl(dto.getThumbnailUrl());
        tvShow.setTrailerUrl(dto.getTrailerUrl());
        tvShow.setCast(dto.getCast());
        tvShow.setMaturityRating(dto.getMaturityRating());
        tvShow.setFeatured(dto.isFeatured());

        if (dto.getGenreIds() != null) {
            tvShow.setGenres(genreRepository.findAllById(dto.getGenreIds()).stream().collect(Collectors.toSet()));
        }
        if (dto.getActorIds() != null) {
            tvShow.setActors(actorRepository.findAllById(dto.getActorIds()).stream().collect(Collectors.toSet()));
        }
        if (dto.getDirectorIds() != null) {
            tvShow.setDirectors(directorRepository.findAllById(dto.getDirectorIds()).stream().collect(Collectors.toSet()));
        }
        if (dto.getProducerIds() != null) {
            tvShow.setProducers(producerRepository.findAllById(dto.getProducerIds()).stream().collect(Collectors.toSet()));
        }
        if (dto.getCategoryId() != null) {
            tvShow.setCategory(categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + dto.getCategoryId())));
        }
        return tvShow;
    }

    private TvShowResponseDto mapToResponse(TVShow tvShow) {
        return TvShowResponseDto.builder()
                .id(tvShow.getId())
                .title(tvShow.getTitle())
                .description(tvShow.getDescription())
                .thumbnailUrl(tvShow.getThumbnailUrl())
                .trailerUrl(tvShow.getTrailerUrl())
                .genres(tvShow.getGenres().stream()
                        .map(g -> new GenreDto(g.getId(), g.getName()))
                        .collect(Collectors.toSet()))
                .cast(tvShow.getCast())
                .maturityRating(tvShow.getMaturityRating())
                .featured(tvShow.isFeatured())
                .seasons(tvShow.getSeasons() != null
                        ? tvShow.getSeasons().stream()
                        .map(s -> SeasonDto.builder()
                                .id(s.getId())
                                .seasonNumber(s.getSeasonNumber())
                                .tvShowId(tvShow.getId())
                                .episodes(s.getEpisodes().stream()
                                        .map(e -> EpisodeDto.builder()
                                                .id(e.getId())
                                                .episodeNumber(e.getEpisodeNumber())
                                                .title(e.getTitle())
                                                .description(e.getDescription())
                                                .duration(e.getDuration())
                                                .contentUrl(e.getContentUrl())
                                                .thumbnailUrl(e.getThumbnailUrl())
                                                .seasonId(s.getId())
                                                .build())
                                        .collect(Collectors.toList()))
                                .build())
                        .collect(Collectors.toList())
                        : null)
                .actors(tvShow.getActors().stream()
                        .map(a -> ActorDto.builder()
                                .id(a.getId())
                                .name(a.getName())
                                .profileImageUrl(a.getProfileImageUrl())
                                .build())
                        .collect(Collectors.toSet()))
                .directors(tvShow.getDirectors().stream()
                        .map(d -> DirectorDto.builder()
                                .id(d.getId())
                                .name(d.getName())
                                .profileImageUrl(d.getProfileImageUrl())
                                .build())
                        .collect(Collectors.toSet()))
                .producers(tvShow.getProducers().stream()
                        .map(p -> ProducerDto.builder()
                                .id(p.getId())
                                .name(p.getName())
                                .profileImageUrl(p.getProfileImageUrl())
                                .build())
                        .collect(Collectors.toSet()))
                .category(tvShow.getCategory() != null
                        ? new CategoryDto(tvShow.getCategory().getId(), tvShow.getCategory().getName())
                        : null)
                .build();
    }

}