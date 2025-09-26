package com.company.BingeBox_backend_application.user_service.client;


import com.company.BingeBox_backend_application.user_service.dtos.MovieResponseDto;
import com.company.BingeBox_backend_application.user_service.dtos.TvShowResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "catalog-service", url="http://localhost:8091/catalog"
)
public interface CatalogClient {

    @GetMapping("/movies/{id}")
    MovieResponseDto getMovieById(@PathVariable("id") Long id);

    @GetMapping("/TvShows/{id}")
    TvShowResponseDto getTvShowById(@PathVariable("id") Long id);
}
