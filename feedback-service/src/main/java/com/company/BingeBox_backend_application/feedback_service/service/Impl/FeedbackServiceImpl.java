package com.company.BingeBox_backend_application.feedback_service.service.Impl;

import com.company.BingeBox_backend_application.feedback_service.dtos.FeedbackRequestDto;
import com.company.BingeBox_backend_application.feedback_service.dtos.FeedbackResponseDto;
import com.company.BingeBox_backend_application.feedback_service.dtos.FeedbackSummaryDto;
import com.company.BingeBox_backend_application.feedback_service.entity.Feedback;
import com.company.BingeBox_backend_application.feedback_service.enums.ContentType;
import com.company.BingeBox_backend_application.feedback_service.repository.FeedbackRepository;
import com.company.BingeBox_backend_application.feedback_service.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final ModelMapper modelMapper;

    @Override
    public FeedbackResponseDto addFeedback(Long feedbackId, FeedbackRequestDto feedbackRequestDto) {
        log.info("Adding feedback for contentId={} contentType={} by userId={}",
               feedbackRequestDto.getContentId(),feedbackRequestDto.getContentType(), feedbackRequestDto.getUserId());

        //prevent duplicates feedback
        if(feedbackRepository.existsByUserIdAndContentIdAndContentType(feedbackRequestDto.getUserId(),
                feedbackRequestDto.getContentId(), ContentType.valueOf(String.valueOf(feedbackRequestDto.getContentType())))){

            throw new RuntimeException("User already submitted feedback for this content");
        }

        Feedback feedback = modelMapper.map(feedbackRequestDto, Feedback.class);
        Feedback saved = feedbackRepository.save(feedback);

        return modelMapper.map(saved, FeedbackResponseDto.class);
    }

    @Override
    public FeedbackResponseDto updateFeedback(Long feedbackId, FeedbackRequestDto feedbackRequestDto) {
        return null;
    }

    @Override
    public void deleteFeedback(Long feedbackId) {

    }

    @Override
    public List<FeedbackResponseDto> getFeedbackByContent(Long contentId, String contentType) {
        return List.of();
    }

    @Override
    public List<FeedbackResponseDto> getFeedbackByUser(Long userId) {
        return List.of();
    }

    @Override
    public FeedbackSummaryDto getFeedbackSummary(Long contentId, String contentType) {
        return null;
    }
}
