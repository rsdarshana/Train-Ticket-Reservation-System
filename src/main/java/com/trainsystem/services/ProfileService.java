package com.trainsystem.services;

import com.trainsystem.model.User;
import com.trainsystem.model.UserProfile;
import com.trainsystem.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    private final UserProfileRepository userProfileRepository;

    public ProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public List<UserProfile> getUserProfiles(User user) {
        return userProfileRepository.findByUser(user);
    }

    public UserProfile saveProfile(UserProfile profile) {
        return userProfileRepository.save(profile);
    }

    public Optional<UserProfile> getProfileByIdAndUser(Long id, User user) {
        return userProfileRepository.findByIdAndUser(id, user);
    }

    public UserProfile updateProfile(UserProfile existingProfile, UserProfile updatedProfile) {
        existingProfile.setName(updatedProfile.getName());
        existingProfile.setEmail(updatedProfile.getEmail());
        existingProfile.setGender(updatedProfile.getGender());
        existingProfile.setGovtId(updatedProfile.getGovtId());
        existingProfile.setMealPreference(updatedProfile.getMealPreference());
        existingProfile.setNickname(updatedProfile.getNickname());
        return userProfileRepository.save(existingProfile);
    }

    public void deleteProfile(Long id, User user) {
        UserProfile profile = userProfileRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        userProfileRepository.delete(profile);
    }
}