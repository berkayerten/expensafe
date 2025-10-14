package com.techthrivecatalyst.expensafe.security.api;


import java.util.Optional;

public interface UserService {
    void save(AppUser user);

    Optional<AppUser> getByUsername(String name);
}
