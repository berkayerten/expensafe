package com.techthrivecatalyst.expensafe.security.api;

import java.util.UUID;

public record AppUser(UUID userId, String username, String password) {
}
