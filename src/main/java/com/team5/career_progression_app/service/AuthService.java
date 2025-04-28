package com.team5.career_progression_app.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.team5.career_progression_app.model.User;
import com.team5.career_progression_app.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthService {

    private final GoogleService googleService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public AuthService(GoogleService googleService, JwtService jwtService, UserRepository userRepository) {
        this.googleService = googleService;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> authenticateWithGoogle(String idToken) {
        try {
            GoogleIdToken.Payload payload = googleService.verify(idToken);
            String email = payload.getEmail().trim().toLowerCase();

            logger.info("Attempting login for email: {}", email);

            Optional<User> userOptional = userRepository.findByEmailIgnoreCase(email);

            if (userOptional.isEmpty()) {
                logger.warn("User not found for email: {}", email);
                return ResponseEntity.status(401).body(Map.of(
                    "error", "unauthorized",
                    "message", "User not registered"
                ));
            }

            User user = userOptional.get();
            String token = jwtService.generateToken(payload, user.getRole().getName());

            return ResponseEntity.ok(Map.of("token", token));

        } catch (Exception e) {
            logger.error("Authentication error", e);
            return ResponseEntity.internalServerError().body(Map.of(
                "error", "authentication_error",
                "message", e.getMessage()
            ));
        }
    }
}
