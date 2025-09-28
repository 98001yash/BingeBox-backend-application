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

    @Builder.Default
    private Set<GenreDto> genres=  Set.of();

    @Builder.Default
    private List<String> cast = List.of();
    private String maturityRating;
    private boolean featured;

    @Builder.Default
    private List<SeasonDto> seasons = List.of();  // includes seasons details

    @Builder.Default
    private Set<ActorDto> actors = Set.of();

    @Builder.Default
    private Set<DirectorDto> directors = Set.of();

    @Builder.Default
    private Set<ProducerDto> producers= Set.of();
    private CategoryDto category;
}
