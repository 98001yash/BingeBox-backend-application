package com.company.BingeBox_backend_application.feedback_service.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProducerDto {

    private Long id;

    private String name;
    private String profileImageUrl;
}
