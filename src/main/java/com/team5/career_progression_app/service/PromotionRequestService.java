package com.team5.career_progression_app.service;

import com.team5.career_progression_app.dto.*;
import com.team5.career_progression_app.exception.InvalidRequestException;
import com.team5.career_progression_app.exception.ResourceNotFoundException;
import com.team5.career_progression_app.model.*;
import com.team5.career_progression_app.repository.PromotionRequestRepository;
import com.team5.career_progression_app.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromotionRequestService {

    private final PromotionRequestRepository promotionRequestRepository;
    private final TaskService taskService;
    private final AIAnalysisService aiAnalysisService;
    private final NotificationService notificationService;

    public List<PromotionRequestDTO> getPromotionRequestsByUserId(Integer userId) {
        List<PromotionRequest> requests = promotionRequestRepository.findByUserId(userId);

        return requests.stream()
                .filter(req -> req.getStatus() == PromotionStatus.APPROVED)
                .map(PromotionRequestDTO::new)
                .toList();
    }

    public List<ActivePromotionRequestDTO> getActivePromotionRequests() {
        List<PromotionRequest> requests = promotionRequestRepository.findByStatusOrderByCreatedAtDesc(PromotionStatus.PENDING);
        
        return requests.stream()
                .map(ActivePromotionRequestDTO::new)
                .collect(Collectors.toList());
    }

    public PromotionRequestDetailsDTO getPromotionRequestDetails(Integer promotionRequestId) {
        PromotionRequest promotionRequest = getPromotionRequestOrThrow(promotionRequestId);
        Integer userId = promotionRequest.getUser().getId();
        
        List<TaskDTO> taskDTOs = taskService.getAllTasksForUser(userId);
        List<TaskCommentDTO> taskCommentDTOs = taskService.getCommentsForUser(userId);
        
        return buildPromotionRequestDetailsDTO(promotionRequest, taskDTOs, taskCommentDTOs);
    }

    private PromotionRequestDetailsDTO buildPromotionRequestDetailsDTO(PromotionRequest promotionRequest, 
                                                                      List<TaskDTO> taskDTOs, 
                                                                      List<TaskCommentDTO> taskCommentDTOs) {
        PromotionRequestDetailsDTO detailsDTO = new PromotionRequestDetailsDTO(promotionRequest);
        detailsDTO.setTasks(taskDTOs);
        detailsDTO.setTaskComments(taskCommentDTOs);
        return detailsDTO;
    }

    public String generateAIAnalysis(Integer promotionRequestId) {
        PromotionRequest promotionRequest = getPromotionRequestOrThrow(promotionRequestId);
        User user = promotionRequest.getUser();
        
        return aiAnalysisService.generateAIAnalysisForUser(user.getId());
    }

    public void approvePromotionRequest(Integer promotionRequestId, String message) {
        PromotionRequest promotionRequest = promotionRequestRepository.findById(promotionRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("PromotionRequest not found with id: " + promotionRequestId));
        
        if (promotionRequest.getStatus() != PromotionStatus.PENDING) {
            throw new InvalidRequestException("Promotion request is not in PENDING status");
        }
        
        promotionRequest.setStatus(PromotionStatus.APPROVED);
        promotionRequestRepository.save(promotionRequest);
        
        notificationService.notifyPromotionApproved(promotionRequest.getUser(), message);
    }

    public void rejectPromotionRequest(Integer promotionRequestId, String message) {
        PromotionRequest promotionRequest = promotionRequestRepository.findById(promotionRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("PromotionRequest not found with id: " + promotionRequestId));
        
        if (promotionRequest.getStatus() != PromotionStatus.PENDING) {
            throw new InvalidRequestException("Promotion request is not in PENDING status");
        }
        
        if (message == null || message.trim().isEmpty()) {
            throw new InvalidRequestException("Rejection message is required");
        }
        
        promotionRequest.setStatus(PromotionStatus.REJECTED);
        promotionRequestRepository.save(promotionRequest);
        
        notificationService.notifyPromotionRejected(promotionRequest.getUser(), message);
    }

    public void updatePromotionRequestStatus(Integer promotionRequestId, PromotionStatus status) {
        PromotionRequest promotionRequest = promotionRequestRepository.findById(promotionRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("PromotionRequest not found with id: " + promotionRequestId));
        promotionRequest.setStatus(status);
        promotionRequestRepository.save(promotionRequest);
    }

    private PromotionRequest getPromotionRequestOrThrow(Integer promotionRequestId) {
        return promotionRequestRepository.findById(promotionRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Promotion request not found"));
    }
}
