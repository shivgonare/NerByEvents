package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    // Retrieve all feedback associated with a specific event
    List<Feedback> findByEventId(Long eventId);

    // Calculate the average rating for a specific event
    @Query("SELECT AVG(f.rating) FROM Feedback f WHERE f.event.id = :eventId")
    Double findAverageRatingByEventId(@Param("eventId") Long eventId);

    // Check if a feedback already exists for a given user and event
    boolean existsByUserIdAndEventId(Long userId, Long eventId);
}
