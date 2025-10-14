package com.techthrivecatalyst.expensafe.security;

import com.techthrivecatalyst.expensafe.security.api.UserExtractor;
import java.util.UUID;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import static java.util.UUID.fromString;

@Component
public class UserExtractorImpl implements UserExtractor {

    private final UserRepository userRepository;

    public UserExtractorImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((User) principal).getUsername();
        AppUserEntity appUserEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        return fromString(appUserEntity.getId());
    }

}
