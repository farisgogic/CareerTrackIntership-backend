package com.team5.career_progression_app.restController;

import com.team5.career_progression_app.dto.ApiResponse;
import com.team5.career_progression_app.dto.FilterCountDTO;
import com.team5.career_progression_app.dto.NotificationDTO;
import com.team5.career_progression_app.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/{userId}")
    public ApiResponse<List<NotificationDTO>> getNotifications(
            @PathVariable Integer userId,
            @RequestParam(required = false) String filter) {

        List<NotificationDTO> notifications = notificationService.getAllForUser(userId, filter);
        return new ApiResponse<>(true, "Notifications fetched successfully", notifications);
    }

    @GetMapping("/{userId}/filters")
    public ApiResponse<List<String>> getAvailableFilters(@PathVariable Integer userId) {
        List<String> filters = notificationService.getAvailableFilters(userId);
        return new ApiResponse<>(true, "Available filters fetched", filters);
    }

    @GetMapping("/{userId}/unread-count")
    public ApiResponse<Integer> getUnreadCount(@PathVariable Integer userId) {
        Integer count = notificationService.getUnreadCount(userId);
        return new ApiResponse<>(true, "Unread notification count fetched", count);
    }

    @GetMapping("/{userId}/filter-counts")
    public ApiResponse<List<FilterCountDTO>> getFilterCounts(@PathVariable Integer userId) {
        List<FilterCountDTO> filterCounts = notificationService.getFilterCounts(userId);
        return new ApiResponse<>(true, "Filter counts fetched", filterCounts);
    }

    @PostMapping("/{notificationId}/read")
    public ApiResponse<Void> markAsRead(@PathVariable Integer notificationId) {
        notificationService.markAsRead(notificationId);
        return new ApiResponse<>(true, "Notification marked as read.", null);
    }

    @PostMapping("/{userId}/mark-all-read")
    public ApiResponse<Void> markAllAsRead(@PathVariable Integer userId) {
        notificationService.markAllAsRead(userId);
        return new ApiResponse<>(true, "All notifications marked as read.", null);
    }
}
