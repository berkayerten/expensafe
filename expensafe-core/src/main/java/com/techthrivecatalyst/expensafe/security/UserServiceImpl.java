package com.techthrivecatalyst.expensafe.security;

import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public void save(AppUserDto user) {
        AppUser appUser = AppUser.create(user.username(), user.password());
        repo.save(appUser);
    }

    @Override
    public Optional<AppUserDto> getByUsername(String username) {
        return repo.findByUsername(username)
                .map(this::toDto);
    }

    private AppUserDto toDto(AppUser appUser) {
        return new AppUserDto(appUser.getUsername(), appUser.getPassword());
    }
}
