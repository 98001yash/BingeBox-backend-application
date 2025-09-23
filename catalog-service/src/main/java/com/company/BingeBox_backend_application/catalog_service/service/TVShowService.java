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
}
