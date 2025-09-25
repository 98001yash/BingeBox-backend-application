package com.company.BingeBox_backend_application.catalog_service.service.Impl;


import com.company.BingeBox_backend_application.catalog_service.dtos.ActorDto;
import com.company.BingeBox_backend_application.catalog_service.entity.Actor;
import com.company.BingeBox_backend_application.catalog_service.exceptions.ResourceNotFoundException;
import com.company.BingeBox_backend_application.catalog_service.repository.ActorRepository;
import com.company.BingeBox_backend_application.catalog_service.service.ActorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {

    private final ActorRepository actorRepository;
    private final ModelMapper modelMapper;


    @Override
    public ActorDto createActor(ActorDto actorDto) {
        Actor actor = modelMapper.map(actorDto, Actor.class);
        Actor savedActor = actorRepository.save(actor);
        return modelMapper.map(savedActor, ActorDto.class);
    }

    @Override
    public ActorDto getActorById(Long id) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Actor not found with id " + id));
        return modelMapper.map(actor, ActorDto.class);
    }

    @Override
    public List<ActorDto> getAllActors() {
        return actorRepository.findAll()
                .stream()
                .map(actor -> modelMapper.map(actor, ActorDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ActorDto updateActor(Long id, ActorDto actorDto) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Actor not found with id " + id));

        actor.setName(actorDto.getName());
        actor.setProfileImageUrl(actorDto.getProfileImageUrl());

        Actor updatedActor = actorRepository.save(actor);
        return modelMapper.map(updatedActor, ActorDto.class);
    }

    @Override
    public void deleteActor(Long id) {
      Actor actor = actorRepository.findById(id)
              .orElseThrow(()->new ResourceNotFoundException("Actor not found with id: "+id));
    }
}
