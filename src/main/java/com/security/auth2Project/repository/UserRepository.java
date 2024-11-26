package com.security.auth2Project.repository;

import com.security.auth2Project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    @Override
    Optional<User> findByEmail(String email);
}
