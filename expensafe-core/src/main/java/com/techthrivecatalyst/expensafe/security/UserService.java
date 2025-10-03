package com.techthrivecatalyst.expensafe.security;


import java.util.Optional;

public interface UserService {
    void save(AppUserDto user);

    Optional<AppUserDto> getByUsername(String name);
}
