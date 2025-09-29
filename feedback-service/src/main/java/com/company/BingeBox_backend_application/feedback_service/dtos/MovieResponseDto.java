package com.company.BingeBox_backend_application.feedback_service.dtos;


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
    @Builder.Default
    private Set<GenreDto> genres = Set.of();

    @Builder.Default
    private List<String> cast = List.of();
    private String maturityRating;
    private boolean featured;

    @Builder.Default
    private Set<ActorDto> actors = Set.of();

    @Builder.Default
    private Set<DirectorDto> directors = Set.of();

    @Builder.Default
    private Set<ProducerDto> producers = Set.of();
    private CategoryDto category;
}
