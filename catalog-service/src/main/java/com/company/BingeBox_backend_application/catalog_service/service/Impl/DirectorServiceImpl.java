package com.company.BingeBox_backend_application.catalog_service.service.Impl;

import com.company.BingeBox_backend_application.catalog_service.dtos.DirectorDto;
import com.company.BingeBox_backend_application.catalog_service.entity.Director;
import com.company.BingeBox_backend_application.catalog_service.repository.DirectorRepository;
import com.company.BingeBox_backend_application.catalog_service.service.DirectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class DirectorServiceImpl implements DirectorService {

    private final DirectorRepository directorRepository;
    @Override
    public DirectorDto createDirector(DirectorDto dto) {
       Director director = mapToEntity(dto);
       return mapToDto(directorRepository.save(director));
    }

    @Override
    public DirectorDto updateDirector(Long id, DirectorDto dto) {
        Director director = directorRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Director not found"));

        director.setName(dto.getName());
        director.setProfileImageUrl(dto.getProfileImageUrl());

        return mapToDto(directorRepository.save(director));
    }

    @Override
    public DirectorDto getDirectorById(Long id) {
        Director director = directorRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Director not found"));
        return mapToDto(director);
    }

    @Override
    public List<DirectorDto> getAllDirectors() {
        return directorRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteDirector(Long id) {
        if(!directorRepository.existsById(id)){
            throw new RuntimeException("Director not found");
        }
        directorRepository.deleteById(id);
    }

    private DirectorDto mapToDto(Director director) {
        return DirectorDto.builder()
                .id(director.getId())
                .name(director.getName())
                .profileImageUrl(director.getProfileImageUrl())
                .build();
    }

    private Director mapToEntity(DirectorDto dto) {
        return Director.builder()
                .id(dto.getId())
                .name(dto.getName())
                .profileImageUrl(dto.getProfileImageUrl())
                .build();
    }
}
