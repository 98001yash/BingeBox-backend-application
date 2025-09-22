package com.company.BingeBox_backend_application.catalog_service.dtos;



import lombok.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TvShowResponseDto {

    private Long id;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String trailerUrl;
    private Set<GenreDto> genres;
    private List<String> cast;
    private String maturityRating;
    private boolean featured;
    private List<SeasonDto> seasons;   // includes seasons details
    private Set<ActorDto> actors;
    private Set<DirectorDto> directors;
    private Set<ProducerDto> producers;
    private CategoryDto category;
}
