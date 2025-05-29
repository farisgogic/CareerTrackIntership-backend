package com.team5.career_progression_app.service;

import com.team5.career_progression_app.dto.PromotionRequestDTO;
import com.team5.career_progression_app.model.PromotionRequest;
import com.team5.career_progression_app.model.PromotionStatus;
import com.team5.career_progression_app.repository.PromotionRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionRequestService {

    private final PromotionRequestRepository promotionRequestRepository;

    public List<PromotionRequestDTO> getPromotionRequestsByUserId(Integer userId) {
        List<PromotionRequest> requests = promotionRequestRepository.findByUserId(userId);

        return requests.stream()
                .filter(req -> req.getStatus() == PromotionStatus.APPROVED)
                .map(PromotionRequestDTO::new)
                .toList();
    }
}
