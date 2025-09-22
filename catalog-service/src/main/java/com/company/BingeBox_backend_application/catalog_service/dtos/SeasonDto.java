package com.company.BingeBox_backend_application.catalog_service.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeasonDto {

    private Long id;
    private int seasonNumber;
    private Long tvShowId;
    private List<EpisodeDto> episodes;
}
