package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Feedback;
import com.example.demo.repository.FeedbackRepository;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepo;

    // Retrieve feedback for a given event
    public List<Feedback> getByEvent(Long eventId) {
        return feedbackRepo.findByEventId(eventId);
    }

    // Save new feedback
    public Feedback save(Feedback feedback) {
        return feedbackRepo.save(feedback);
    }
    
    // Calculate average rating for an event
    public Double getAverageRating(Long eventId) {
        return feedbackRepo.findAverageRatingByEventId(eventId);
    }

    // Check if the user already submitted feedback for an event
    public boolean hasUserAlreadySubmitted(Long userId, Long eventId) {
        return feedbackRepo.existsByUserIdAndEventId(userId, eventId);
    }

    // Update an existing feedback by id
    public Feedback updateFeedback(Long id, Feedback updatedFeedback) {
        Feedback existing = feedbackRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Feedback not found"));
        existing.setComment(updatedFeedback.getComment());
        existing.setRating(updatedFeedback.getRating());
        // Optionally, update user and event if needed
        return feedbackRepo.save(existing);
    }

    // Delete feedback by id
    public void deleteFeedback(Long id) {
        if (!feedbackRepo.existsById(id)) {
            throw new RuntimeException("Feedback not found");
        }
        feedbackRepo.deleteById(id);
    }
}
