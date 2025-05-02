package com.example.demo.service;

import com.example.demo.entity.Event;
import com.example.demo.entity.User;
import org.springframework.stereotype.Service;

@Service
public class NotificationService 
{
    public void sendNotification(User user, Event event) 
    {
        // For simplicity, just log it for now
        System.out.printf("Notifying user %s about available seats in event: %s%n", user.getEmail(), event.getName());
        
        // Integrate with email/SMS/Push service here
    }
}
