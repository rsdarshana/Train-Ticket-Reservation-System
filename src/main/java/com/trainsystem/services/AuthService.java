package com.trainsystem.services;

import com.trainsystem.model.User;
import com.trainsystem.model.UserPreference;
import com.trainsystem.model.ScheduleFilter;
import com.trainsystem.repository.UserPreferenceRepository;
import com.trainsystem.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserPreferenceRepository userPreferenceRepository;

    public AuthService(UserRepository userRepository,
                     UserPreferenceRepository userPreferenceRepository) {
        this.userRepository = userRepository;
        this.userPreferenceRepository = userPreferenceRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Transactional
    public User registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        // No password encoding (plain text for now)
        User savedUser = userRepository.save(user);

        // Create new UserPreference with default values
        UserPreference preference = new UserPreference();
        preference.setUser(savedUser);
        preference.setSavedFilter(new ScheduleFilter()); // Initialize with empty filter
        
        userPreferenceRepository.save(preference);

        return savedUser;
    }
}