package com.techthrivecatalyst.expensafe.security;

import com.techthrivecatalyst.expensafe.security.api.AppUser;
import com.techthrivecatalyst.expensafe.security.api.UserService;
import java.util.Optional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import static java.util.UUID.fromString;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repo;
    private final ApplicationEventPublisher eventPublisher;

    public UserServiceImpl(UserRepository repo, ApplicationEventPublisher eventPublisher) {
        this.repo = repo;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void save(AppUser user) {
        AppUserEntity appUserEntity = AppUserEntity.create(user.username(), user.password());
        appUserEntity = repo.save(appUserEntity);
        publishCreation(appUserEntity.getId());
    }

    private void publishCreation(String userId) {
        UserCreatedEvent event = new UserCreatedEvent(fromString(userId));
        eventPublisher.publishEvent(event);
    }

    @Override
    public Optional<AppUser> getByUsername(String username) {
        return repo.findByUsername(username)
                .map(this::toDto);
    }

    private AppUser toDto(AppUserEntity appUserEntity) {
        return new AppUser(fromString(appUserEntity.getId()), appUserEntity.getUsername(), appUserEntity.getPassword());
    }
}
