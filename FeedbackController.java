package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Feedback;
import com.example.demo.service.FeedbackService;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    // Submit new feedback (with duplicate check)
    @PostMapping
    public Feedback submit(@RequestBody Feedback feedback) {
        Long userId = feedback.getUser().getId();
        Long eventId = feedback.getEvent().getId();

        if (feedbackService.hasUserAlreadySubmitted(userId, eventId)) {
            throw new RuntimeException("User has already submitted feedback for this event.");
        }
        return feedbackService.save(feedback);
    }

    // Update an existing feedback
    @PutMapping("/{id}")
    public Feedback updateFeedback(@PathVariable Long id, @RequestBody Feedback updatedFeedback) {
        return feedbackService.updateFeedback(id, updatedFeedback);
    }

    // Delete a feedback by its id
    @DeleteMapping("/{id}")
    public String deleteFeedback(@PathVariable Long id) {
        feedbackService.deleteFeedback(id);
        return "Feedback deleted successfully";
    }

    // Get all feedback for a specific event
    @GetMapping("/event/{eventId}")
    public List<Feedback> byEvent(@PathVariable Long eventId) {
        return feedbackService.getByEvent(eventId);
    }

    // Get average rating for a specific event
    @GetMapping("/event/{eventId}/average-rating")
    public Double getAverageRating(@PathVariable Long eventId) {
        return feedbackService.getAverageRating(eventId);
    }
}
