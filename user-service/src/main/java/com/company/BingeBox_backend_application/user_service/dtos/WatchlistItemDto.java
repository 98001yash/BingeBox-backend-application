package com.company.BingeBox_backend_application.user_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WatchlistItemDto {
    private Long id;
    private Long contentId;
    private String contentType;


    private MovieResponseDto movie;        // populated if contentType = "MOVIE"
    private TvShowResponseDto tvShow;   // populated if contentType = "TVSHOW"
}