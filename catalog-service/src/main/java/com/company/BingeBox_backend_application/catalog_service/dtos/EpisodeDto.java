package com.company.BingeBox_backend_application.catalog_service.dtos;


import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EpisodeDto {

    private Long id;
    private int episodeNumber;
    private String title;
    private String description;
    private int duration;
    private String contentUrl;
    private String thumbnailUrl;
    private Long seasonId;
}
