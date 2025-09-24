package com.company.BingeBox_backend_application.catalog_service.service.Impl;


import com.company.BingeBox_backend_application.catalog_service.dtos.EpisodeDto;
import com.company.BingeBox_backend_application.catalog_service.entity.Episode;
import com.company.BingeBox_backend_application.catalog_service.entity.Season;
import com.company.BingeBox_backend_application.catalog_service.exceptions.ResourceNotFoundException;
import com.company.BingeBox_backend_application.catalog_service.repository.EpisodeRepository;
import com.company.BingeBox_backend_application.catalog_service.repository.SeasonRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EpisodeServiceImpl implements EpisodeService{

    private final EpisodeRepository episodeRepository;
    private final SeasonRepository seasonRepository;
    private final ModelMapper modelMapper;


    @Override
    public EpisodeDto createEpisode(EpisodeDto dto) {
        Season season = seasonRepository.findById(dto.getSeasonId())
                .orElseThrow(() -> new ResourceNotFoundException("Season not found with id " + dto.getSeasonId()));

        Episode episode = modelMapper.map(dto, Episode.class);
        episode.setSeason(season);

        Episode savedEpisode = episodeRepository.save(episode);
        return modelMapper.map(savedEpisode, EpisodeDto.class);
    }

    @Override
    public EpisodeDto getEpisodeById(Long id) {
       Episode episode = episodeRepository.findById(id)
               .orElseThrow(()->new ResourceNotFoundException("Episode not found with id "+id));

       return modelMapper.map(episode, EpisodeDto.class);
    }

    @Override
    public List<EpisodeDto> getAllEpisodes() {
        return  episodeRepository.findAll()
                .stream()
                .map(ep -> modelMapper.map(ep,EpisodeDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public EpisodeDto updateEpisode(Long id, EpisodeDto dto) {
        Episode episode = episodeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Episode not found with id " + id));

        Season season = seasonRepository.findById(dto.getSeasonId())
                .orElseThrow(() -> new ResourceNotFoundException("Season not found with id " + dto.getSeasonId()));

        episode.setEpisodeNumber(dto.getEpisodeNumber());
        episode.setTitle(dto.getTitle());
        episode.setDescription(dto.getDescription());
        episode.setDuration(dto.getDuration());
        episode.setContentUrl(dto.getContentUrl());
        episode.setThumbnailUrl(dto.getThumbnailUrl());
        episode.setSeason(season);

        Episode updatedEpisode = episodeRepository.save(episode);
        return modelMapper.map(updatedEpisode, EpisodeDto.class);
    }

    @Override
    public void deleteEpisode(Long id) {
        Episode episode = episodeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Episode not found with id " + id));
        episodeRepository.delete(episode);
    }
}
