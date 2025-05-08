package com.team5.career_progression_app.service;

import com.team5.career_progression_app.dto.FilterCountDTO;
import com.team5.career_progression_app.dto.NotificationDTO;
import com.team5.career_progression_app.model.Notification;
import com.team5.career_progression_app.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<NotificationDTO> getAllForUser(Integer userId, String filter) {
        List<Notification> notifications;

        if (filter == null || filter.equals("All")) {
            notifications = notificationRepository.findByRecipientIdOrderByCreatedAtDesc(userId);
        } else if (filter.equals("Unread")) {
            notifications = notificationRepository.findByRecipientIdAndReadFalseOrderByCreatedAtDesc(userId);
        } else {
            notifications = notificationRepository.findByRecipientIdAndMessageContainingIgnoreCaseOrderByCreatedAtDesc(
                userId,
                getSearchTermForFilter(filter)
            );
        }

        notifications.sort((n1, n2) -> {
            if (!n1.isRead() && n2.isRead()) {
                return -1;
            }
            if (n1.isRead() && !n2.isRead()) {
                return 1;
            }
            return n2.getCreatedAt().compareTo(n1.getCreatedAt());
        });

        return notifications.stream()
                .map(NotificationDTO::new)
                .collect(Collectors.toList());
    }

    private String getSearchTermForFilter(String filter) {
        switch (filter.toLowerCase()) {
            case "task": return "task";
            case "comment": return "comment";
            case "promotion": return "promot";
            case "meeting": return "meeting";
            case "message": return "message";
            case "alert": return "alert";
            case "review": return "review";
            case "error": return "error";
            default: return filter;
        }
    }

    public List<String> getAvailableFilters(Integer userId) {
        return List.of("All", "Unread", "Task", "Comment", "Promotion", "Meeting", "Message", "Alert", "Review", "Error");
    }

    public Integer getUnreadCount(Integer userId) {
        return notificationRepository.countByRecipientIdAndReadFalse(userId);
    }

    public List<FilterCountDTO> getFilterCounts(Integer userId) {
        List<FilterCountDTO> filterCounts = new ArrayList<>();
        filterCounts.add(new FilterCountDTO("All", notificationRepository.countByRecipientId(userId)));
        filterCounts.add(new FilterCountDTO("Unread", notificationRepository.countByRecipientIdAndReadFalse(userId)));

        filterCounts.add(new FilterCountDTO("Task", notificationRepository.countByRecipientIdAndMessageContainingIgnoreCase(userId, "task")));
        filterCounts.add(new FilterCountDTO("Comment", notificationRepository.countByRecipientIdAndMessageContainingIgnoreCase(userId, "comment")));
        filterCounts.add(new FilterCountDTO("Promotion", notificationRepository.countByRecipientIdAndMessageContainingIgnoreCase(userId, "promot")));
        filterCounts.add(new FilterCountDTO("Meeting", notificationRepository.countByRecipientIdAndMessageContainingIgnoreCase(userId, "meeting")));
        filterCounts.add(new FilterCountDTO("Message", notificationRepository.countByRecipientIdAndMessageContainingIgnoreCase(userId, "message")));
        filterCounts.add(new FilterCountDTO("Alert", notificationRepository.countByRecipientIdAndMessageContainingIgnoreCase(userId, "alert")));
        filterCounts.add(new FilterCountDTO("Review", notificationRepository.countByRecipientIdAndMessageContainingIgnoreCase(userId, "review")));
        filterCounts.add(new FilterCountDTO("Error", notificationRepository.countByRecipientIdAndMessageContainingIgnoreCase(userId, "error")));

        return filterCounts;
    }

    public void markAsRead(Integer notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
            .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    public void markAllAsRead(Integer userId) {
        List<Notification> notifications = notificationRepository.findByRecipientIdOrderByCreatedAtDesc(userId);
        notifications.forEach(n -> n.setRead(true));
        notificationRepository.saveAll(notifications);
    }
}
