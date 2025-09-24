package com.company.BingeBox_backend_application.catalog_service.controller;

import com.company.BingeBox_backend_application.catalog_service.dtos.TvShowRequestDto;
import com.company.BingeBox_backend_application.catalog_service.dtos.TvShowResponseDto;
import com.company.BingeBox_backend_application.catalog_service.service.TVShowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalog/TvShows")
@RequiredArgsConstructor
@Slf4j
public class TvShowController {

    private final TVShowService tvShowService;

    @PostMapping
    public ResponseEntity<TvShowResponseDto>  createTvShow(@RequestBody TvShowRequestDto tvShowRequestDto){
        log.info("Creatng a new TvShow with title: {}",tvShowRequestDto.getTitle());
        return  ResponseEntity.ok(tvShowService.createTvShow(tvShowRequestDto));
    }
}
