package com.company.BingeBox_backend_application.catalog_service.service.Impl;

import com.company.BingeBox_backend_application.catalog_service.dtos.EpisodeDto;

import java.util.List;

public interface EpisodeService {

    EpisodeDto createEpisode(EpisodeDto episodeDto);
    EpisodeDto getEpisodeById(Long id);

    List<EpisodeDto> getAllEpisodes();

    EpisodeDto updateEpisode(Long id, EpisodeDto episodeDto);

    void deleteEpisode(Long id);
}
