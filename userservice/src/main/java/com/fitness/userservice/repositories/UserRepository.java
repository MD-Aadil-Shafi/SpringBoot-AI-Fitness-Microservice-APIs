package com.fitness.userservice.repositories;

import com.fitness.userservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByEmail(String email);
    User findByEmail(String email);

    boolean existsByKeycloakId(String userId);
}
