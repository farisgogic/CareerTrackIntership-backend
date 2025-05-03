package com.team5.career_progression_app.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.team5.career_progression_app.model.Role;
import com.team5.career_progression_app.model.User;
import com.team5.career_progression_app.repository.RoleRepository;
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
    private final RoleRepository roleRepository;
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public AuthService(GoogleService googleService, JwtService jwtService, UserRepository userRepository,
            RoleRepository roleRepository) {
        this.googleService = googleService;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public ResponseEntity<?> authenticateWithGoogle(String idToken) {
        try {
            GoogleIdToken.Payload payload = googleService.verify(idToken);
            String email = payload.getEmail().trim().toLowerCase();

            logger.info("Attempting login for email: {}", email);

            Optional<User> userOptional = userRepository.findByEmailIgnoreCase(email);

            if (userOptional.isEmpty()) {
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setActive(false);
                newUser.setFirstName((String) payload.get("given_name"));
                newUser.setLastName((String) payload.get("family_name"));
                newUser.setProfilePictureUrl((String) payload.get("picture"));

                Role defaultRole = roleRepository.findRoleByName("USER")
                        .orElseThrow(() -> new RuntimeException("Default role not found"));
                newUser.setRole(defaultRole);

                userRepository.save(newUser);

                logger.info("New user registered and pending activation: {}", email);
                return ResponseEntity.status(403).body(Map.of(
                        "status", "PENDING_APPROVAL",
                        "message", "Profile created. Waiting for admin approval."));

            }

            User user = userOptional.get();

            if (!user.isActive()) {
                logger.warn("Inactive user attempted login: {}", email);
                return ResponseEntity.status(403).body(Map.of(
                        "message", "Your profile is not activated yet."));
            }

            String newProfilePictureUrl = (String) payload.get("picture");
            if (!newProfilePictureUrl.equals(user.getProfilePictureUrl())) {
                user.setProfilePictureUrl(newProfilePictureUrl);
                userRepository.save(user);
            }

            String token = jwtService.generateToken(payload, user.getRole().getName());
            return ResponseEntity.ok(Map.of("token", token));

        } catch (Exception e) {
            logger.error("Authentication error", e);
            return ResponseEntity.internalServerError().body(Map.of(
                    "error", "authentication_error",
                    "message", e.getMessage()));
        }
    }

}
