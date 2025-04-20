package com.trainsystem.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "USER_PREFERENCE")
@Data
public class UserPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;
    
    @Embedded
    private ScheduleFilter savedFilter;
    
    // Other preference fields if needed
}