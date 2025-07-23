// src/main/java/com/trainsystem/repository/adapter/UserProfileRepositoryAdapter.java
package com.trainsystem.repository.adapter;

import com.trainsystem.model.User;
import com.trainsystem.model.UserProfile;
import com.trainsystem.repository.UserProfileRepository;
import com.trainsystem.repository.port.UserProfileRepositoryPort;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class UserProfileRepositoryAdapter implements UserProfileRepositoryPort {

    private final UserProfileRepository userProfileRepository;

    public UserProfileRepositoryAdapter(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public List<UserProfile> findByUser(User user) {
        return userProfileRepository.findByUser(user);
    }

    @Override
    public Optional<UserProfile> findByIdAndUser(Long id, User user) {
        return userProfileRepository.findByIdAndUser(id, user);
    }

    @Override
    public void deleteByIdAndUser(Long id, User user) {
        userProfileRepository.deleteByIdAndUser(id, user);
    }
}
