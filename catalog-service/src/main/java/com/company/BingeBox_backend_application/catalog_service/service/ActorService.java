package com.company.BingeBox_backend_application.catalog_service.service;

import com.company.BingeBox_backend_application.catalog_service.dtos.ActorDto;

import java.util.List;

public interface ActorService {

    ActorDto createActor(ActorDto actorDto);

    ActorDto getActorById(Long id);

    List<ActorDto> getAllActors();

    ActorDto updateActor(Long id, ActorDto actorDto);

    void deleteActor(Long id);
}
