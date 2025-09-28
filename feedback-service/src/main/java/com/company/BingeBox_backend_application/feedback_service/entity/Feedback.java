package com.company.BingeBox_backend_application.feedback_service.entity;


import com.company.BingeBox_backend_application.feedback_service.enums.ContentType;
import com.company.BingeBox_backend_application.feedback_service.enums.FeedbackType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "feedbacks",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "content_id", "content_type"})
        }
)

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "content_id", nullable = false)
    private Long contentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_type", nullable = false)
    private ContentType contentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "feedback_type", nullable = false)
    private FeedbackType feedbackType;

    @Column(name = "rating")
    private  Integer rating;

    @Column(name = "comment", length = 1000)
    private String comment;
}
