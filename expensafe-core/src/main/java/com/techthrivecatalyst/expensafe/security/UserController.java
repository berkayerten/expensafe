package com.techthrivecatalyst.expensafe.security;

import java.security.Principal;
import java.util.Map;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService service;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;


    public UserController(UserService service, PasswordEncoder encoder, JwtUtil jwtUtil) {
        this.service = service;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public String register(@RequestBody AppUserDto userRequest) {
        AppUserDto user = new AppUserDto(userRequest.username(), encoder.encode(userRequest.password()));
        service.save(user);
        return "User registered!";
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody AppUserDto loginRequest) {
        var dbUser = service.getByUsername(loginRequest.username())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(loginRequest.password(), dbUser.password())) {
            throw new IllegalArgumentException("Invalid password");
        }

        String token = jwtUtil.generateToken(dbUser.username());
        return Map.of("token", token);
    }

    @GetMapping("/me")
    public AppUserDto me(Principal principal) {
        return service.getByUsername(principal.getName()).orElseThrow();
    }
}
