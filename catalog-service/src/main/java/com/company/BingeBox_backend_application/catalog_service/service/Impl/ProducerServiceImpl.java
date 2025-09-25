package com.company.BingeBox_backend_application.catalog_service.service.Impl;

import com.company.BingeBox_backend_application.catalog_service.dtos.ProducerDto;
import com.company.BingeBox_backend_application.catalog_service.entity.Producer;
import com.company.BingeBox_backend_application.catalog_service.exceptions.ResourceNotFoundException;
import com.company.BingeBox_backend_application.catalog_service.repository.ProducerRepository;
import com.company.BingeBox_backend_application.catalog_service.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProducerServiceImpl implements ProducerService {

    private final ProducerRepository producerRepository;
    private final ModelMapper modelMapper;


    @Override
    public ProducerDto createProducer(ProducerDto producerDto) {
        Producer producer = modelMapper.map(producerDto, Producer.class);
        Producer savedProducer = producerRepository.save(producer);

        return modelMapper.map(savedProducer, ProducerDto.class);
    }

    @Override
    public ProducerDto getProducerById(Long id) {
        Producer producer = producerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producer not found with id " + id));
        return modelMapper.map(producer, ProducerDto.class);
    }

    @Override
    public List<ProducerDto> getAllProducers() {
        return producerRepository.findAll()
                .stream()
                .map(producer -> modelMapper.map(producer, ProducerDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProducerDto updateProducer(Long id, ProducerDto producerDto) {
        Producer producer = producerRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("producer not found with id:"+id));

        producer.setName(producerDto.getName());
        producer.setProfileImageUrl(producerDto.getProfileImageUrl());

        Producer updatedProducer = producerRepository.save(producer);
        return modelMapper.map(updatedProducer, ProducerDto.class);
    }

    @Override
    public void deleteProducer(Long id) {
        Producer producer = producerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producer not found with id " + id));
        producerRepository.delete(producer);
    }
}
