package com.team5.career_progression_app.restController;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.team5.career_progression_app.model.User;
import com.team5.career_progression_app.repository.RoleRepository;
import com.team5.career_progression_app.repository.UserRepository;
import com.team5.career_progression_app.service.GoogleService;
import com.team5.career_progression_app.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final GoogleService googleService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(GoogleService googleService, JwtService jwtService, 
                        UserRepository userRepository, RoleRepository roleRepository) {
        this.googleService = googleService;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/google")
public ResponseEntity<?> loginWithGoogle(@RequestBody Map<String, String> request) {
    String idToken = request.get("idToken");
    
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

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok().body(Map.of("message", "Logout successful"));
    }
}