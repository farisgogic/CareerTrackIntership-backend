package com.team5.career_progression_app.restController;

import com.team5.career_progression_app.dto.ApiResponse;
import com.team5.career_progression_app.dto.UserDTO;
import com.team5.career_progression_app.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/inactive")
    public ResponseEntity<List<UserDTO>> getInactiveUsers() {
        return ResponseEntity.ok(userService.getInactiveUsers());
    }

    @GetMapping("/active")
    public ResponseEntity<List<UserDTO>> getActiveUsers() {
        return ResponseEntity.ok(userService.getActiveUsers());
    }

    @PostMapping("/activate/{id}")
    public ApiResponse<UserDTO> activateUser(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String token) {
        return userService.changeUserActivation(id, stripBearerToken(token), true);
    }

    @PostMapping("/deactivate/{id}")
    public ApiResponse<UserDTO> deactivateUser(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String token) {
        return userService.changeUserActivation(id, stripBearerToken(token), false);
    }

    private String stripBearerToken(String token) {
        return token.startsWith("Bearer ") ? token.substring(7) : token;
    }

    @GetMapping("/filter")
    public ResponseEntity<List<UserDTO>> getUsersWithFilters(
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) String name) {
        
        return ResponseEntity.ok(userService.getUsersWithFilters(active, name));
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<?> updateUserRole(@PathVariable Integer id, @RequestBody String newRoleName) {
        userService.updateUserRole(id, newRoleName.toUpperCase());
        return ResponseEntity.ok("User role updated successfully.");
    }

    @DeleteMapping("/{id}/role")
    public ResponseEntity<?> deleteUserRole(@PathVariable Integer id) {
        userService.deleteUserRoleAndDeactivate(id);
        return ResponseEntity.ok("User role removed, reset to USER, and user deactivated.");
    }

}
