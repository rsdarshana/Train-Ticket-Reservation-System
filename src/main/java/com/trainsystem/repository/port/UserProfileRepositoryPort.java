// src/main/java/com/trainsystem/repository/port/UserProfileRepositoryPort.java
package com.trainsystem.repository.port;

import com.trainsystem.model.User;
import com.trainsystem.model.UserProfile;
import java.util.List;
import java.util.Optional;

public interface UserProfileRepositoryPort {
    List<UserProfile> findByUser(User user);
    Optional<UserProfile> findByIdAndUser(Long id, User user);
    void deleteByIdAndUser(Long id, User user);
}
