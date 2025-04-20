package com.trainsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trainsystem.model.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
   
}