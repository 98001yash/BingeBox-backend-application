package com.company.BingeBox_backend_application.user_service.dtos;


import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieResponseDto {

    private Long id;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String trailerUrl;
    private String contentUrl;
    private int releaseYear;
    private int duration;
    private Set<GenreDto> genres;
    private List<String> cast;
    private String maturityRating;
    private boolean featured;
    private Set<ActorDto> actors;
    private Set<DirectorDto> directors;
    private Set<ProducerDto> producers;
    private CategoryDto category;
}
