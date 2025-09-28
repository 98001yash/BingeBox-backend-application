package com.company.BingeBox_backend_application.feedback_service.repository;

import com.company.BingeBox_backend_application.feedback_service.entity.Feedback;
import com.company.BingeBox_backend_application.feedback_service.enums.ContentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback,Long> {

    List<Feedback> findByContentIdAndContentType(Long contentId, ContentType contentType);

    List<Feedback> findByUserId(Long userId);

    boolean existsByUserIdAndContentIdAndContentType(Long userId,Long contentId, ContentType contentType);
}
