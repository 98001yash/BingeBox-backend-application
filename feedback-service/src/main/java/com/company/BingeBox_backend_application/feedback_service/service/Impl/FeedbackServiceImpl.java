package com.company.BingeBox_backend_application.feedback_service.service.Impl;

import com.company.BingeBox_backend_application.feedback_service.auth.UserContextHolder;
import com.company.BingeBox_backend_application.feedback_service.client.CatalogClient;
import com.company.BingeBox_backend_application.feedback_service.dtos.FeedbackRequestDto;
import com.company.BingeBox_backend_application.feedback_service.dtos.FeedbackResponseDto;
import com.company.BingeBox_backend_application.feedback_service.dtos.FeedbackSummaryDto;
import com.company.BingeBox_backend_application.feedback_service.dtos.MovieResponseDto;
import com.company.BingeBox_backend_application.feedback_service.dtos.TvShowResponseDto;
import com.company.BingeBox_backend_application.feedback_service.entity.Feedback;
import com.company.BingeBox_backend_application.feedback_service.enums.ContentType;
import com.company.BingeBox_backend_application.feedback_service.enums.FeedbackType;
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
    private final CatalogClient catalogClient; // ✅ Feign client

    @Override
    public FeedbackResponseDto addFeedback(Long feedbackId, FeedbackRequestDto feedbackRequestDto) {
        Long userId = UserContextHolder.getCurrentUserId();
        log.info("Adding feedback for contentId={} contentType={} by userId={}",
                feedbackRequestDto.getContentId(),
                feedbackRequestDto.getContentType(),
                userId
        );

        // --- Validate content exists in catalog-service ---
        validateContent(feedbackRequestDto.getContentId(), feedbackRequestDto.getContentType());

        // prevent duplicates
        if (feedbackRepository.existsByUserIdAndContentIdAndContentType(
                userId,
                feedbackRequestDto.getContentId(),
                feedbackRequestDto.getContentType())) {
            throw new RuntimeException("User already submitted feedback for this content");
        }

        Feedback feedback = modelMapper.map(feedbackRequestDto, Feedback.class);
        feedback.setUserId(userId);

        Feedback saved = feedbackRepository.save(feedback);
        log.info("Feedback created successfully with id={}", saved.getId());

        return modelMapper.map(saved, FeedbackResponseDto.class);
    }

    @Override
    public FeedbackResponseDto updateFeedback(Long feedbackId, FeedbackRequestDto feedbackRequestDto) {
        Long userId = UserContextHolder.getCurrentUserId();
        log.info("Updating feedback id={} for userId={}", feedbackId, userId);

        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found with id: " + feedbackId));

        if (!feedback.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized: You can only update your own feedback");
        }

        // Validate content exists in catalog-service
        validateContent(feedbackRequestDto.getContentId(), feedbackRequestDto.getContentType());

        feedback.setRating(feedbackRequestDto.getRating());
        feedback.setComment(feedbackRequestDto.getComment());
        feedback.setFeedbackType(feedbackRequestDto.getFeedbackType());

        Feedback updated = feedbackRepository.save(feedback);
        log.info("Feedback updated successfully id={}", feedbackId);

        return modelMapper.map(updated, FeedbackResponseDto.class);
    }

    @Override
    public void deleteFeedback(Long feedbackId) {
        Long userId = UserContextHolder.getCurrentUserId();
        log.warn("Deleting feedback id={} by userId={}", feedbackId, userId);

        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found with id " + feedbackId));

        if (!feedback.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized: You can only delete your own feedback");
        }

        feedbackRepository.delete(feedback);
        log.info("Feedback deleted successfully id={}", feedbackId);
    }

    @Override
    public List<FeedbackResponseDto> getFeedbackByContent(Long contentId, String contentType) {
        log.debug("Fetching feedback for contentId={}, contentType={}", contentId, contentType);

        ContentType type = ContentType.valueOf(contentType.toUpperCase());
        List<Feedback> feedbackList = feedbackRepository.findByContentIdAndContentType(contentId, type);

        return feedbackList.stream()
                .map(f -> modelMapper.map(f, FeedbackResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedbackResponseDto> getFeedbackByUser(Long userId) {
        log.debug("Fetching feedback for userId={}", userId);

        List<Feedback> feedbacklist = feedbackRepository.findByUserId(userId);
        return feedbacklist.stream()
                .map(f -> modelMapper.map(f, FeedbackResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public FeedbackSummaryDto getFeedbackSummary(Long contentId, String contentType) {
        log.debug("Calculating feedback summary for contentId={} contentType={}", contentId, contentType);

        ContentType type = ContentType.valueOf(contentType.toUpperCase());
        List<Feedback> feedbackList = feedbackRepository.findByContentIdAndContentType(contentId, type);

        long totalLikes = feedbackList.stream()
                .filter(f -> f.getFeedbackType() == FeedbackType.LIKE)
                .count();

        long totalDislikes = feedbackList.stream()
                .filter(f -> f.getFeedbackType() == FeedbackType.DISLIKE)
                .count();

        double avgRating = feedbackList.stream()
                .filter(f -> f.getRating() != null)
                .mapToInt(Feedback::getRating)
                .average()
                .orElse(0.0);

        long totalComments = feedbackList.stream()
                .filter(f -> f.getComment() != null && !f.getComment().isBlank())
                .count();

        return FeedbackSummaryDto.builder()
                .contentId(contentId)
                .contentType(type.name())
                .totalLikes(totalLikes)
                .totalDislikes(totalDislikes)
                .averageRating(avgRating)
                .totalComments(totalComments)
                .build();
    }

    // ----------------- helper -----------------
    private void validateContent(Long contentId, ContentType contentType) {
        try {
            if (contentType == ContentType.MOVIE) {
                MovieResponseDto movie = catalogClient.getMovieById(contentId);
                if (movie == null) throw new RuntimeException("Movie not found in catalog-service");
            } else if (contentType == ContentType.TVSHOW) {
                TvShowResponseDto tvShow = catalogClient.getTvShowById(contentId);
                if (tvShow == null) throw new RuntimeException("TV Show not found in catalog-service");
            } else {
                throw new RuntimeException("Invalid content type: " + contentType);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch content from catalog-service: " + e.getMessage());
        }
    }
}
