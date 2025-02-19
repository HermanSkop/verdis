package com.verdis.repositories;

import com.verdis.models.account.User;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByActivationToken(String activationToken);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String username);
}