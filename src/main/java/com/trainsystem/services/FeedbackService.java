package com.trainsystem.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.trainsystem.model.Feedback;
import com.trainsystem.repository.FeedbackRepository;

@Service
public class FeedbackService {
    
    private final FeedbackRepository feedbackRepository;
    
    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }
    
    public Feedback saveFeedback(Feedback feedback) {
        // Ensure submission time is set
        if (feedback.getSubmissionTime() == null) {
            feedback.setSubmissionTime(LocalDateTime.now());
        }
        
        // Ensure we have default values for required fields
        if (feedback.getRating() == null) {
            feedback.setRating(1);
        }
        
        if (feedback.getCategory() == null || feedback.getCategory().isEmpty()) {
            feedback.setCategory("Other");
        }
        
        return feedbackRepository.save(feedback);
    }
    
   
}