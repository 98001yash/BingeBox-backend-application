package com.company.BingeBox_backend_application.feedback_service.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackSummaryDto {

    private Long contentId;

    private String contentType;

    private Long totalLikes;
    private Long totalDislikes;
    private Double averageRating;
    private Long totalComments;
}
