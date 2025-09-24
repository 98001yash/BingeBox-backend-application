package com.company.BingeBox_backend_application.catalog_service.service.Impl;


import com.company.BingeBox_backend_application.catalog_service.dtos.SeasonDto;
import com.company.BingeBox_backend_application.catalog_service.entity.Season;
import com.company.BingeBox_backend_application.catalog_service.entity.TVShow;
import com.company.BingeBox_backend_application.catalog_service.exceptions.ResourceNotFoundException;
import com.company.BingeBox_backend_application.catalog_service.repository.SeasonRepository;
import com.company.BingeBox_backend_application.catalog_service.repository.TVShowRepository;
import com.company.BingeBox_backend_application.catalog_service.service.SeasonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeasonServiceImpl implements SeasonService {

    private final SeasonRepository seasonRepository;
    private final TVShowRepository tvShowRepository;

    @Override
    public SeasonDto createSeason(SeasonDto seasonDto) {
        TVShow tvShow = tvShowRepository.findById(seasonDto.getTvShowId())
                .orElseThrow(()->new ResourceNotFoundException("Tv show not found with id: "+seasonDto.getTvShowId()));

        Season season = Season.builder()
                .seasonNumber(seasonDto.getSeasonNumber())
                .tvShow(tvShow)
                .build();

        Season savedSeason = seasonRepository.save(season);
        return mapToDto(savedSeason);
    }

    @Override
    public SeasonDto getSeasonById(Long id) {
        Season season = seasonRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Season not found with id: "+id));
        return mapToDto(season);
    }

    @Override
    public List<SeasonDto> getAllSeasons() {
        return seasonRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public SeasonDto updateSeason(Long id, SeasonDto seasonDto) {
        Season season = seasonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Season not found with id: " + id));

        if (seasonDto.getTvShowId() != null) {
            TVShow tvShow = tvShowRepository.findById(seasonDto.getTvShowId())
                    .orElseThrow(() -> new ResourceNotFoundException("TV Show not found with id: " + seasonDto.getTvShowId()));
            season.setTvShow(tvShow);
        }

        season.setSeasonNumber(seasonDto.getSeasonNumber());

        Season updatedSeason = seasonRepository.save(season);
        return mapToDto(updatedSeason);
    }

    @Override
    public void deleteSeason(Long id) {
        Season season = seasonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Season not found with id: " + id));
        seasonRepository.delete(season);
    }


    private SeasonDto mapToDto(Season season) {
        return SeasonDto.builder()
                .id(season.getId())
                .seasonNumber(season.getSeasonNumber())
                .tvShowId(season.getTvShow().getId())
                .episodes(season.getEpisodes() != null ? season.getEpisodes().stream()
                        .map(e -> com.company.BingeBox_backend_application.catalog_service.dtos.EpisodeDto.builder()
                                .id(e.getId())
                                .title(e.getTitle())
                                .description(e.getDescription())
                                .duration(e.getDuration())
                                .contentUrl(e.getContentUrl())
                                .thumbnailUrl(e.getThumbnailUrl())
                                .episodeNumber(e.getEpisodeNumber())
                                .seasonId(season.getId())
                                .build())
                        .collect(Collectors.toList()) : null)
                .build();
    }
}
