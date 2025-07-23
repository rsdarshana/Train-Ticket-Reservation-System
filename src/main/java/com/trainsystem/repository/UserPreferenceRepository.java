package com.trainsystem.repository;
import java.util.Optional;


import com.trainsystem.model.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPreferenceRepository extends JpaRepository<UserPreference, Long> {
    Optional<UserPreference> findByUserUsername(String username);
}