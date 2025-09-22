package com.company.BingeBox_backend_application.catalog_service.dtos;



import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseEntityDto {


    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
