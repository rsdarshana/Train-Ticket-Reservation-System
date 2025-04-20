package com.trainsystem.repository;

import com.trainsystem.model.User;
import com.trainsystem.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    List<UserProfile> findByUser(User user);
    Optional<UserProfile> findByIdAndUser(Long id, User user);
    void deleteByIdAndUser(Long id, User user);
}