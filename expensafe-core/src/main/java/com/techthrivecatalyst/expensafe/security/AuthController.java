package com.techthrivecatalyst.expensafe.security;

import com.techthrivecatalyst.expensafe.security.api.AppUser;
import com.techthrivecatalyst.expensafe.security.api.UserService;
import java.security.Principal;
import java.util.Map;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class AuthController {

    private final UserService service;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserService service, PasswordEncoder encoder, JwtUtil jwtUtil) {
        this.service = service;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public String register(@RequestBody AppUser userRequest) {
        AppUser user = new AppUser(null, userRequest.username(), encoder.encode(userRequest.password()));
        service.save(user);
        return "User registered!";
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody AppUser loginRequest) {
        var dbUser = service.getByUsername(loginRequest.username())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(loginRequest.password(), dbUser.password())) {
            throw new IllegalArgumentException("Invalid password");
        }

        String token = jwtUtil.generateToken(dbUser.username());
        return Map.of("token", token);
    }

    @GetMapping("/me")
    public AppUser me(Principal principal) {
        return service.getByUsername(principal.getName()).orElseThrow();
    }
}
