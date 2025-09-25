package com.company.BingeBox_backend_application.catalog_service.service;

import com.company.BingeBox_backend_application.catalog_service.dtos.TvShowRequestDto;
import com.company.BingeBox_backend_application.catalog_service.dtos.TvShowResponseDto;

import java.util.List;

public interface TVShowService {
    TvShowResponseDto createTvShow(TvShowRequestDto tvShowRequestDto);

    TvShowResponseDto getTvShowById(Long id);

    List<TvShowResponseDto> getAllTvShows();

    TvShowResponseDto updateTvShow(Long id, TvShowRequestDto tvShowRequestDto);

    void deleteTvShow(Long id);

    // --- Relationship endpoints ---
    TvShowResponseDto addGenreToTvShow(Long tvShowId, Long genreId);
    TvShowResponseDto removeGenreFromTvShow(Long tvShowId, Long genreId);

    TvShowResponseDto addActorToTvShow(Long tvShowId, Long actorId);
    TvShowResponseDto removeActorFromTvShow(Long tvShowId, Long actorId);

    TvShowResponseDto addDirectorToTvShow(Long tvShowId, Long directorId);
    TvShowResponseDto removeDirectorFromTvShow(Long tvShowId, Long directorId);

    TvShowResponseDto addProducerToTvShow(Long tvShowId, Long producerId);
    TvShowResponseDto removeProducerFromTvShow(Long tvShowId, Long producerId);

    TvShowResponseDto removeCategoryFromTvShow(Long tvShowId);
    TvShowResponseDto addCategoryToTvShow(Long tvShowId, Long categoryId);
}
