package com.team5.career_progression_app.restController;

import com.team5.career_progression_app.dto.PromotionRequestDTO;
import com.team5.career_progression_app.service.PromotionRequestService;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/promotions")
public class PromotionRequestController {

    private PromotionRequestService promotionRequestService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PromotionRequestDTO>> getPromotionRequestsByUser(@PathVariable Integer userId) {
        List<PromotionRequestDTO> requests = promotionRequestService.getPromotionRequestsByUserId(userId);
        return ResponseEntity.ok(requests);
    }
}
