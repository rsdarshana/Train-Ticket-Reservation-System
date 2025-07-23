package com.trainsystem.services;

import com.trainsystem.model.User;
import com.trainsystem.model.UserPreference;
import com.trainsystem.repository.UserPreferenceRepository;
import com.trainsystem.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final UserPreferenceRepository userPreferenceRepository;
    
    public UserService(UserRepository userRepository, 
                      UserPreferenceRepository userPreferenceRepository) {
        this.userRepository = userRepository;
        this.userPreferenceRepository = userPreferenceRepository;
    }
    
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public Optional<UserPreference> getUserPreferences(String username) {
        return userPreferenceRepository.findByUserUsername(username);
    }
    
    public void updateUserPreferences(String username, UserPreference updatedPreference) {
        userPreferenceRepository.findByUserUsername(username).ifPresent(preference -> {
            // Only update the remaining preferences
            if (updatedPreference.getSavedFilter() != null) {
                preference.setSavedFilter(updatedPreference.getSavedFilter());
            }
            userPreferenceRepository.save(preference);
        });
    }
    
    // Additional helper methods if needed
    public void saveUserPreference(UserPreference preference) {
        userPreferenceRepository.save(preference);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}