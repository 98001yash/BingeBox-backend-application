package com.company.BingeBox_backend_application.catalog_service.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActorDto {

    private Long id;

    private String name;

    private String profileImageUrl;
}
