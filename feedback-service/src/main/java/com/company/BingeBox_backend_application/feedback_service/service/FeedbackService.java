package com.company.BingeBox_backend_application.feedback_service.service;

import com.company.BingeBox_backend_application.feedback_service.dtos.FeedbackRequestDto;
import com.company.BingeBox_backend_application.feedback_service.dtos.FeedbackResponseDto;
import com.company.BingeBox_backend_application.feedback_service.dtos.FeedbackSummaryDto;

import java.util.List;

public interface FeedbackService {

    FeedbackResponseDto addFeedback(Long feedbackId, FeedbackRequestDto feedbackRequestDto);

    FeedbackResponseDto updateFeedback(Long feedbackId, FeedbackRequestDto feedbackRequestDto);

    void deleteFeedback(Long feedbackId);

    List<FeedbackResponseDto> getFeedbackByContent(Long contentId, String contentType);

    List<FeedbackResponseDto> getFeedbackByUser(Long userId);

    FeedbackSummaryDto getFeedbackSummary(Long contentId, String contentType);
}
