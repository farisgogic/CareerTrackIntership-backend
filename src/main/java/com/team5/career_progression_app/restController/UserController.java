package com.team5.career_progression_app.restController;

import com.team5.career_progression_app.dto.UserDTO;
import com.team5.career_progression_app.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/inactive")
    public ResponseEntity<List<UserDTO>> getInactiveUsers() {
        return ResponseEntity.ok(userService.getInactiveUsers());
    }

    @PostMapping("/activate/{id}")
    public ResponseEntity<?> activateUser(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String token) {

        String rawToken = token.replace("Bearer ", "");
        return userService.activateUserWithResponse(id, rawToken);
    }

}
