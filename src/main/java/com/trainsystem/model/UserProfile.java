// src/main/java/com/trainsystem/model/UserProfile.java
package com.trainsystem.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "USER_PROFILES")
@Data
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String email;
    
    @Column
    private String gender;
    
    @Column(name = "govt_id", length = 12)
    private String govtId;
    
    @Column(name = "meal_preference")
    private String mealPreference;
    
    @Column
    private String nickname;
}