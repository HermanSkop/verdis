package com.verdis.repositories;

import com.verdis.models.account.Account;
import jakarta.validation.constraints.Pattern;
import org.apache.el.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
    Optional<Account> findByEmail(String email);
    Optional<Account> findByActivationToken(String token);

    boolean existsByEmail(String email);
}