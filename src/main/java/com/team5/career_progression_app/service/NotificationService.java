package com.team5.career_progression_app.service;

import com.team5.career_progression_app.dto.NotificationFilterCountDTO;
import com.team5.career_progression_app.dto.NotificationDTO;
import com.team5.career_progression_app.dto.PaginatedResponse;
import com.team5.career_progression_app.exception.NotificationNotFoundException;
import com.team5.career_progression_app.model.Notification;
import com.team5.career_progression_app.model.NotificationFilter;
import com.team5.career_progression_app.model.NotificationType;
import com.team5.career_progression_app.model.User;
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

    public PaginatedResponse<NotificationDTO> getAllForUser(Integer userId, NotificationFilter filter, int page, int size) {
        List<Notification> notifications;

        if (filter == null || filter == NotificationFilter.ALL) {
            notifications = notificationRepository.findByRecipientIdOrderByCreatedAtDesc(userId);
        } else if (filter == NotificationFilter.UNREAD) {
            notifications = notificationRepository.findByRecipientIdAndReadFalseOrderByCreatedAtDesc(userId);
        } else {
            NotificationType type = NotificationType.valueOf(filter.name());
            notifications = notificationRepository.findByRecipientIdAndTypeOrderByCreatedAtDesc(userId, type);
        }

        notifications.sort((n1, n2) -> {
            if (!n1.isRead() && n2.isRead()) return -1;
            if (n1.isRead() && !n2.isRead()) return 1;
            return n2.getCreatedAt().compareTo(n1.getCreatedAt());
        });

        int totalCount = notifications.size();
        int totalPages  = (int) Math.ceil((double) totalCount / size);
        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, totalCount);

        List<NotificationDTO> paginatedData = notifications.subList(startIndex, endIndex)
                .stream()
                .map(NotificationDTO::new)
                .collect(Collectors.toList());

        return new PaginatedResponse<>(paginatedData, totalCount, page, size, totalPages);
    }

    public List<NotificationFilter> getAvailableFilters(Integer userId) {
        List<NotificationFilter> filters = new ArrayList<>();
        filters.add(NotificationFilter.ALL);
        filters.add(NotificationFilter.UNREAD);

        for (NotificationType type : NotificationType.values()) {
            filters.add(NotificationFilter.valueOf(type.name()));
        }

        return filters;
    }

    public Integer getUnreadCount(Integer userId) {
        return notificationRepository.countByRecipientIdAndReadFalse(userId);
    }

    public List<NotificationFilterCountDTO> getFilterCounts(Integer userId) {
        List<NotificationFilterCountDTO> filterCounts = new ArrayList<>();
        filterCounts.add(new NotificationFilterCountDTO(NotificationFilter.ALL.name(), notificationRepository.countByRecipientId(userId)));
        filterCounts.add(new NotificationFilterCountDTO(NotificationFilter.UNREAD.name(), notificationRepository.countByRecipientIdAndReadFalse(userId)));

        for (NotificationType type : NotificationType.values()) {
            int count = notificationRepository.countByRecipientIdAndType(userId, type);
            filterCounts.add(new NotificationFilterCountDTO(type.name(), count));
        }

        return filterCounts;
    }

    public void markAsRead(Integer notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
            .orElseThrow(() -> new NotificationNotFoundException(notificationId));
        notification.setRead(true);
        notificationRepository.save(notification);
    }
    

    public void markAllAsRead(Integer userId) {
        List<Notification> notifications = notificationRepository.findByRecipientIdOrderByCreatedAtDesc(userId);
        notifications.forEach(n -> n.setRead(true));
        notificationRepository.saveAll(notifications);
    }

    public void notifyTaskReceived(User recipient, String taskDetails) {
        createNotification(recipient, "Task Received: " + taskDetails, "Task received", NotificationType.TASK);
    }

    public void notifyTaskFinished(User lead, String taskDetails) {
        createNotification(lead, "Task Finished: " + taskDetails, "Task finished", NotificationType.TASK);
    }

    public void notifyFeedbackReceived(User recipient, String feedbackDetails) {
        createNotification(recipient, "Feedback Received: " + feedbackDetails, "Feedback received", NotificationType.FEEDBACK);
    }

    public void notifyTaskChanged(User recipient, String taskDetails) {
        createNotification(recipient, "Task Changed: " + taskDetails, "Task changed", NotificationType.TASK);
    }

    public void notifyPromotionReceived(User recipient, String promotionDetails) {
        createNotification(recipient, "Promotion Received: " + promotionDetails, "Promotion", NotificationType.PROMOTION);
    }

    public void notifyError(User recipient, String errorDetails) {
        createNotification(recipient, "Error: " + errorDetails, "Error", NotificationType.ERROR);
    }

    public void notifyAlert(User recipient, String alertDetails) {
        createNotification(recipient, "Alert: " + alertDetails, "Alert", NotificationType.ALERT);
    }

    private void createNotification(User recipient, String message, String title, NotificationType type) {
        Notification notification = new Notification();
        notification.setRecipient(recipient);
        notification.setMessage(message);
        notification.setTitle(title);
        notification.setType(type);
        notificationRepository.save(notification);
    }
}
