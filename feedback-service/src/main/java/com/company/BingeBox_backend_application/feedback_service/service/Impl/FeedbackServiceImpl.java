package com.company.BingeBox_backend_application.feedback_service.service.Impl;

import com.company.BingeBox_backend_application.feedback_service.dtos.FeedbackRequestDto;
import com.company.BingeBox_backend_application.feedback_service.dtos.FeedbackResponseDto;
import com.company.BingeBox_backend_application.feedback_service.dtos.FeedbackSummaryDto;
import com.company.BingeBox_backend_application.feedback_service.entity.Feedback;
import com.company.BingeBox_backend_application.feedback_service.enums.ContentType;
import com.company.BingeBox_backend_application.feedback_service.exceptions.ResourceNotFoundException;
import com.company.BingeBox_backend_application.feedback_service.repository.FeedbackRepository;
import com.company.BingeBox_backend_application.feedback_service.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final ModelMapper modelMapper;

    @Override
    public FeedbackResponseDto addFeedback(Long feedbackId, FeedbackRequestDto feedbackRequestDto) {
        log.info("Adding feedback for contentId={} contentType={} by userId={}",
                feedbackRequestDto.getContentId(),
                feedbackRequestDto.getContentType(),
                feedbackRequestDto.getUserId()
        );

        // prevent duplicates
        if (feedbackRepository.existsByUserIdAndContentIdAndContentType(
                feedbackRequestDto.getUserId(),
                feedbackRequestDto.getContentId(),
                feedbackRequestDto.getContentType())) {
            throw new RuntimeException("User already submitted feedback for this content");
        }

        Feedback feedback = modelMapper.map(feedbackRequestDto, Feedback.class);
        Feedback saved = feedbackRepository.save(feedback);

        log.info("Feedback created successfully with id={}", saved.getId());
        return modelMapper.map(saved, FeedbackResponseDto.class);
    }



    @Override
    public FeedbackResponseDto updateFeedback(Long feedbackId, FeedbackRequestDto feedbackRequestDto) {
        log.info("Updating feedback id={} for userId={}", feedbackId, feedbackRequestDto.getUserId());

        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found with id: " + feedbackId));

        // update fields
        feedback.setRating(feedbackRequestDto.getRating());
        feedback.setComment(feedbackRequestDto.getComment());
        feedback.setFeedbackType(feedbackRequestDto.getFeedbackType());

        Feedback updated = feedbackRepository.save(feedback);
        log.info("Feedback updated successfully id={}", feedbackId);

        return modelMapper.map(updated, FeedbackResponseDto.class);
    }


    @Override
    public void deleteFeedback(Long feedbackId) {
      log.warn("Deleting the feedback Id={}", feedbackId);

      Feedback feedback = feedbackRepository.findById(feedbackId)
              .orElseThrow(()->new ResourceNotFoundException("Feedback not found with id/l "+feedbackId));

      feedbackRepository.delete(feedback);
      log.info("Feedback deleted successfully id={}", feedbackId);
    }

    @Override
    public List<FeedbackResponseDto> getFeedbackByContent(Long contentId, String contentType) {
      log.debug("Fetching feedback for contentId={}, contentType={}",contentId, contentType);

      ContentType type = ContentType.valueOf(contentType.toUpperCase());
      List<Feedback> feedbackList = feedbackRepository.findByContentIdAndContentType(contentId, type);

      return feedbackList.stream()
              .map(f->modelMapper.map(f, FeedbackResponseDto.class))
              .collect(Collectors.toList());
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
