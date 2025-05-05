package com.team5.career_progression_app.restController;

import com.team5.career_progression_app.exception.InvalidRequestException;
import com.team5.career_progression_app.service.AuthService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/google")
    public Map<String, String> loginWithGoogle(@RequestBody Map<String, String> request) {
        String idToken = request.get("idToken");

        if (idToken == null || idToken.isBlank()) {
            logger.warn("Missing idToken in request");
            throw new InvalidRequestException("Missing idToken in request body");
        }

        return authService.authenticateWithGoogle(idToken);
    }


    @PostMapping("/logout")
    public Map<String,String> logout() {
        return Map.of("message", "Logout successful");
    }
}
