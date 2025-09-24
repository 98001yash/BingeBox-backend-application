package com.company.BingeBox_backend_application.catalog_service.service;

import com.company.BingeBox_backend_application.catalog_service.dtos.SeasonDto;

import java.util.List;

public interface SeasonService {

    SeasonDto createSeason(SeasonDto seasonDto);

    SeasonDto getSeasonById(Long id);
    List<SeasonDto> getAllSeasons();
    SeasonDto updateSeason(Long id, SeasonDto seasonDto);

    void deleteSeason(Long id);
}
