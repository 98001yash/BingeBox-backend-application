package com.company.BingeBox_backend_application.catalog_service.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieRequestDto {

    private String title;
    private String description;
    private String thumbnailUrl;
    private String trailerUrl;
    private String contentUrl;
    private int releaseYear;
    private int duration;

    private Set<Long> genreIds;
    private List<String> cast;

    private String maturityRating;
    private boolean featured;

    private Set<Long> actorIds;
    private Set<Long> directorIds;
    private Set<Long> producerIds;
    private Long categoryId;
}
