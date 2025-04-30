package com.team5.career_progression_app.restController;

import com.team5.career_progression_app.model.User;
import com.team5.career_progression_app.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/inactive")
    public ResponseEntity<List<User>> getInactiveUsers() {
        return ResponseEntity.ok(userService.getInactiveUsers());
    }

    @PostMapping("/activate/{id}")
    public ResponseEntity<?> activateUser(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String token) {
        
        try {
            String rawToken = token.replace("Bearer ", "");
            String message = userService.activateUser(id, rawToken);
            
            return ResponseEntity.ok().body(Map.of(
                "success", true,
                "message", message
            ));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "error", e.getMessage()
            ));
        }
    }
}
