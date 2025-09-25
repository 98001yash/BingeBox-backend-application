package com.company.BingeBox_backend_application.catalog_service.service;

import com.company.BingeBox_backend_application.catalog_service.dtos.ProducerDto;

import java.util.List;

public interface ProducerService {

    ProducerDto createProducer(ProducerDto producerDto);

    ProducerDto getProducerById(Long id);
    List<ProducerDto> getAllProducers();

    ProducerDto updateProducer(Long id, ProducerDto producerDto);

    void deleteProducer(Long id);
}
