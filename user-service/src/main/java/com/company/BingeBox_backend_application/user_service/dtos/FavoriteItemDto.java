package com.company.BingeBox_backend_application.user_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavoriteItemDto {
    private Long id;
    private Long contentId;
    private String contentType;


    private MovieResponseDto movie;
    private TvShowResponseDto tvShow;
}
