package com.company.BingeBox_backend_application.feedback_service.dtos;


import com.company.BingeBox_backend_application.feedback_service.enums.ContentType;
import com.company.BingeBox_backend_application.feedback_service.enums.FeedbackType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackRequestDto {

    private Long userId;

    private Long contentId;

    private ContentType contentType;

    private FeedbackType feedbackType;

    private Integer rating;

    private String comment;
}
