package com.company.BingeBox_backend_application.catalog_service.service;

import com.company.BingeBox_backend_application.catalog_service.dtos.DirectorDto;

import java.util.List;

public interface DirectorService {

    DirectorDto createDirector(DirectorDto dto);

    DirectorDto updateDirector(Long id, DirectorDto dto);

    DirectorDto getDirectorById(Long id);

    List<DirectorDto> getAllDirectors();

    void deleteDirector(Long id);
}
