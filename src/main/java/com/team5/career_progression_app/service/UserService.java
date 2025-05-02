package com.team5.career_progression_app.service;

import com.team5.career_progression_app.dto.UserDTO;
import com.team5.career_progression_app.model.Permission;
import com.team5.career_progression_app.model.User;
import com.team5.career_progression_app.repository.PermissionRepository;
import com.team5.career_progression_app.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, JwtService jwtService,
            PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.permissionRepository = permissionRepository;
    }

    public List<UserDTO> getInactiveUsers() {
        return userRepository.findByActiveFalse().stream()
                .map(UserDTO::new)
                .toList();
    }

    public String activateUser(Integer userId, String token) throws AccessDeniedException {
        Permission manageUsersPermission = permissionRepository.findByNameIgnoreCase("MANAGE_USERS")
                .orElseThrow(() -> new IllegalStateException("Permission 'MANAGE_USERS' not found"));

        List<Integer> userPermissionIds = jwtService.extractPermissionIds(token);

        if (!userPermissionIds.contains(manageUsersPermission.getId())) {
            throw new AccessDeniedException("Access Denied - Permission 'MANAGE_USERS' is required");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (user.isActive()) {
            throw new IllegalStateException("User is already active");
        }

        user.setActive(true);
        userRepository.save(user);
        return "User " + user.getEmail() + " has been successfully activated";
    }

    public ResponseEntity<?> activateUserWithResponse(Integer userId, String token) {
        try {
            String message = activateUser(userId, token);
            return ResponseEntity.ok().body(Map.of(
                    "success", true,
                    "message", message));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "error", e.getMessage()));
        }
    }

    public String getProfilePictureFromToken(String token) {
        if (token == null)
            return null;
        return jwtService.extractClaim(token, claims -> claims.get("picture", String.class));
    }
}
