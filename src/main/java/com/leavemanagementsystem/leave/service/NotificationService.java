package com.leavemanagementsystem.leave.service;

import com.leavemanagementsystem.leave.entiy.Notification;
import com.leavemanagementsystem.leave.entiy.User;
import com.leavemanagementsystem.leave.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void sendNotification(User user, String message){

        Notification notification = new Notification();

        notification.setUser(user);
        notification.setMessage(message);

        notificationRepository.save(notification);
    }

    public List<Notification> getUserNotifications(Long userId){
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public void markAsRead(Long id){
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        notification.setReadStatus(true);

        notificationRepository.save(notification);
    }

}
