package com.company.BingeBox_backend_application.feedback_service.controller;


import com.company.BingeBox_backend_application.feedback_service.dtos.FeedbackRequestDto;
import com.company.BingeBox_backend_application.feedback_service.dtos.FeedbackResponseDto;
import com.company.BingeBox_backend_application.feedback_service.dtos.FeedbackSummaryDto;
import com.company.BingeBox_backend_application.feedback_service.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;


    // create feedback
    @PostMapping
    public ResponseEntity<FeedbackResponseDto> createFeedback(@RequestBody FeedbackRequestDto request) {
        log.info("Received request to create feedback: {}", request);
        FeedbackResponseDto response = feedbackService.addFeedback(null, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeedbackResponseDto> updateFeedback(
            @PathVariable Long id,
            @RequestBody FeedbackRequestDto request
    ){
        log.info("Received request to update feedback with id={}", id);
        FeedbackResponseDto response = feedbackService.updateFeedback(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long id){
        log.info("Received request to delete feedback with id={}",id);
        feedbackService.deleteFeedback(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/content/{contentId}")
    public ResponseEntity<List<FeedbackResponseDto>> getFeedbackByContent(
            @PathVariable Long contentId,
            @RequestParam("type") String contentType
    ){
        log.info("Fetching feedbacks for contentId={} of type={}",contentId,contentType);

        List<FeedbackResponseDto> response = feedbackService.getFeedbackByContent(contentId, contentType);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FeedbackResponseDto>> getFeedbackByUser(@PathVariable Long userId){
        log.info("Fetching feedbacks for userId={}", userId);

        List<FeedbackResponseDto> response = feedbackService.getFeedbackByUser(userId);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/summary/{contentId}")
    public ResponseEntity<FeedbackSummaryDto> getFeedbackSummary(
            @PathVariable Long contentId,
            @RequestParam("type") String contentType
    ){
        log.info("Fetching feedback summary for contentId={} of type={}",contentId, contentType);

        FeedbackSummaryDto response = feedbackService.getFeedbackSummary(contentId, contentType);
        return ResponseEntity.ok(response);
    }

}
