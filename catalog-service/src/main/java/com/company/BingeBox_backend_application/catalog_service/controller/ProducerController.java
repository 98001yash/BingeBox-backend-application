package com.company.BingeBox_backend_application.catalog_service.controller;


import com.company.BingeBox_backend_application.catalog_service.dtos.ProducerDto;
import com.company.BingeBox_backend_application.catalog_service.service.ProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog/producers")
@RequiredArgsConstructor
@Slf4j
public class ProducerController {

    private final ProducerService producerService;

    @PostMapping
    public ResponseEntity<ProducerDto> createProducer(@RequestBody ProducerDto producerDto) {
        log.info("Creating new producer: {}", producerDto.getName());
        return ResponseEntity.ok(producerService.createProducer(producerDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProducerDto> getProducerById(@PathVariable Long id) {
        log.info("Fetching producer with id: {}", id);
        return ResponseEntity.ok(producerService.getProducerById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProducerDto>> getAllProducers() {
        log.info("Fetching all producers");
        return ResponseEntity.ok(producerService.getAllProducers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProducerDto> updateProducer(
            @PathVariable Long id,
            @RequestBody ProducerDto producerDto) {
        log.info("Updating producer with id: {}", id);
        return ResponseEntity.ok(producerService.updateProducer(id, producerDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducer(@PathVariable Long id) {
        log.info("Deleting producer with id: {}", id);
        producerService.deleteProducer(id);
        return ResponseEntity.noContent().build();
    }
}
