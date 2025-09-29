package com.company.BingeBox_backend_application.feedback_service.controller;

import com.company.BingeBox_backend_application.feedback_service.auth.RoleAllowed;
import com.company.BingeBox_backend_application.feedback_service.dtos.FeedbackRequestDto;
import com.company.BingeBox_backend_application.feedback_service.dtos.FeedbackResponseDto;
import com.company.BingeBox_backend_application.feedback_service.dtos.FeedbackSummaryDto;
import com.company.BingeBox_backend_application.feedback_service.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedbacks")
@RequiredArgsConstructor
@Slf4j
public class FeedbackController {

    private final FeedbackService feedbackService;

    // ------------------- CREATE -------------------
    @PostMapping
    @RoleAllowed({"USER", "ADMIN"})
    public ResponseEntity<FeedbackResponseDto> addFeedback(@RequestBody FeedbackRequestDto requestDto) {
        log.info("Request to add feedback for contentId={} by current user", requestDto.getContentId());
        FeedbackResponseDto responseDto = feedbackService.addFeedback(null, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // ------------------- UPDATE -------------------
    @PutMapping("/{feedbackId}")
    @RoleAllowed({"USER", "ADMIN"})
    public ResponseEntity<FeedbackResponseDto> updateFeedback(
            @PathVariable Long feedbackId,
            @RequestBody FeedbackRequestDto requestDto) {
        log.info("Request to update feedbackId={} by current user", feedbackId);
        FeedbackResponseDto responseDto = feedbackService.updateFeedback(feedbackId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // ------------------- DELETE -------------------
    @DeleteMapping("/{feedbackId}")
    @RoleAllowed({"USER", "ADMIN"})
    public ResponseEntity<String> deleteFeedback(@PathVariable Long feedbackId) {
        log.info("Request to delete feedbackId={} by current user", feedbackId);
        feedbackService.deleteFeedback(feedbackId);
        return ResponseEntity.ok("Feedback deleted successfully with id: " + feedbackId);
    }

    // ------------------- GET BY CONTENT -------------------
    @GetMapping("/content/{contentId}")
    @RoleAllowed({"USER", "ADMIN"})
    public ResponseEntity<List<FeedbackResponseDto>> getFeedbackByContent(
            @PathVariable Long contentId,
            @RequestParam String contentType) {
        log.info("Fetching feedbacks for contentId={} contentType={}", contentId, contentType);
        List<FeedbackResponseDto> feedbacks = feedbackService.getFeedbackByContent(contentId, contentType);
        return ResponseEntity.ok(feedbacks);
    }

    // ------------------- GET BY USER -------------------
    @GetMapping("/user")
    @RoleAllowed({"USER", "ADMIN"})
    public ResponseEntity<List<FeedbackResponseDto>> getFeedbackByCurrentUser() {
        Long userId = com.company.BingeBox_backend_application.feedback_service.auth.UserContextHolder.getCurrentUserId();
        log.info("Fetching feedbacks for current userId={}", userId);
        List<FeedbackResponseDto> feedbacks = feedbackService.getFeedbackByUser(userId);
        return ResponseEntity.ok(feedbacks);
    }

    // ------------------- SUMMARY -------------------
    @GetMapping("/content/{contentId}/summary")
    @RoleAllowed({"USER", "ADMIN"})
    public ResponseEntity<FeedbackSummaryDto> getFeedbackSummary(
            @PathVariable Long contentId,
            @RequestParam String contentType) {
        log.info("Fetching feedback summary for contentId={} contentType={}", contentId, contentType);
        FeedbackSummaryDto summary = feedbackService.getFeedbackSummary(contentId, contentType);
        return ResponseEntity.ok(summary);
    }
}
