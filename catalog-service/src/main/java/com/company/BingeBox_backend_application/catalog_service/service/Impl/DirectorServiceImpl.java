package com.company.BingeBox_backend_application.catalog_service.service.Impl;

import com.company.BingeBox_backend_application.catalog_service.dtos.DirectorDto;
import com.company.BingeBox_backend_application.catalog_service.entity.Director;
import com.company.BingeBox_backend_application.catalog_service.repository.DirectorRepository;
import com.company.BingeBox_backend_application.catalog_service.service.DirectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


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
        return null;
    }

    @Override
    public DirectorDto getDirectorById(Long id) {
        return null;
    }

    @Override
    public List<DirectorDto> getAllDirectors() {
        return List.of();
    }

    @Override
    public void deleteDirector(Long id) {

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
