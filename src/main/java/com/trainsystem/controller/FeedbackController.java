package com.trainsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trainsystem.model.Feedback;
import com.trainsystem.repository.FeedbackRepository;
import com.trainsystem.services.FeedbackService;

import jakarta.validation.Valid;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {
    
    private final FeedbackService feedbackService;
    
    @Autowired
    private FeedbackRepository feedbackRepository;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }
    
    @GetMapping
    public String showFeedbackForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Feedback feedback = new Feedback();
        feedback.setUsername(userDetails.getUsername());
        // Set default values
        feedback.setRating(3); 
        feedback.setCategory("Website Usability");
        model.addAttribute("feedback", feedback);
        model.addAttribute("username", userDetails.getUsername());
        return "feedback/feedback-form";
    }
    
    @PostMapping
    public String submitFeedback(
            @AuthenticationPrincipal UserDetails userDetails,
            @ModelAttribute Feedback feedback,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {
        
        System.out.println("Received feedback: " + feedback.getUsername() + ", Rating: " + 
                          feedback.getRating() + ", Comment: " + feedback.getComment());
        
        // Skip validation for now to rule it out as the problem
        /*
        if (result.hasErrors()) {
            System.out.println("Validation errors found:");
            result.getAllErrors().forEach(error -> {
                System.out.println(error.getDefaultMessage());
            });
            
            model.addAttribute("username", userDetails.getUsername());
            return "feedback/feedback-form";
        }
        */
        
        feedback.setUsername(userDetails.getUsername());
        
        try {
            // Try direct JdbcTemplate insertion as a fallback
            String sql = "INSERT INTO FEEDBACK (USERNAME, RATING, COMMENT, CATEGORY, SUBMISSION_TIME) VALUES (?, ?, ?, ?, ?)";
            int rowsAffected = jdbcTemplate.update(
                sql,
                feedback.getUsername(),
                feedback.getRating(),
                feedback.getComment(),
                feedback.getCategory(),
                Timestamp.valueOf(LocalDateTime.now())
            );
            
            System.out.println("Direct DB insertion result: " + rowsAffected + " rows affected");
            
            // Also try the service method for completeness
            try {
                Feedback savedFeedback = feedbackService.saveFeedback(feedback);
                System.out.println("Service saved feedback with ID: " + 
                                  (savedFeedback != null ? savedFeedback.getId() : "null"));
            } catch (Exception e) {
                System.err.println("Service method failed but direct insert may have worked: " + e.getMessage());
            }
            
            redirectAttributes.addFlashAttribute("feedbackSuccess", true);
            return "redirect:/dashboard";
        } catch (Exception e) {
            System.err.println("CRITICAL ERROR saving feedback: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("errorMessage", "Database error: " + e.getMessage());
            model.addAttribute("username", userDetails.getUsername());
            return "feedback/feedback-form";
        }
    }
    
    
    }
