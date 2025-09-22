package com.company.BingeBox_backend_application.catalog_service.dtos;


import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TvShowRequestDto {

    private String title;
    private String description;
    private String thumbnailUrl;
    private String trailerUrl;
    private Set<Long> genreIds;
    private List<String> cast;
    private String maturityRating;
    private boolean featured;
    private Set<Long> actorIds;
    private Set<Long> directorIds;
    private Set<Long> producerIds;
    private Long categoryId;

}
