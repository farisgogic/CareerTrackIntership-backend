package com.team5.career_progression_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.team5.career_progression_app.model.Review;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private Integer id;
    private String feedback;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer userId;
    private Integer reviewerId;

    public ReviewDTO(Review review) {
        this(
            review.getId(),
            review.getFeedback(),
            review.getCreatedAt(),
            review.getUpdatedAt(),
            review.getUser().getId(),
            review.getReviewer().getId()
        );
    }
}