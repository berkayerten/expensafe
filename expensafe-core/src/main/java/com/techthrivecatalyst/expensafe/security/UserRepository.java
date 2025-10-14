package com.techthrivecatalyst.expensafe.security;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUserEntity, String> {
    Optional<AppUserEntity> findByUsername(String username);
}
