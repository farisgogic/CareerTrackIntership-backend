package com.team5.career_progression_app.service;

import com.team5.career_progression_app.dto.UserDTO;
import com.team5.career_progression_app.model.Permission;
import com.team5.career_progression_app.model.User;
import com.team5.career_progression_app.repository.PermissionRepository;
import com.team5.career_progression_app.repository.UserRepository;
import com.team5.career_progression_app.specification.UserSpecification;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.data.jpa.domain.Specification;
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

    public List<UserDTO> getActiveUsers() {
        return userRepository.findByActiveTrue().stream()
                .map(UserDTO::new)
                .toList();
    }

    private void checkManageUsersPermission(String token) throws AccessDeniedException {
        Permission manageUsersPermission = permissionRepository.findByNameIgnoreCase("MANAGE_USERS")
                .orElseThrow(() -> new IllegalStateException("Permission 'MANAGE_USERS' not found"));

        List<Integer> userPermissionIds = jwtService.extractPermissionIds(token);

        if (!userPermissionIds.contains(manageUsersPermission.getId())) {
            throw new AccessDeniedException("Access Denied - Permission 'MANAGE_USERS' is required");
        }
    }

    private String toggleUserActivation(Integer userId, boolean activate, String token) throws AccessDeniedException {
        checkManageUsersPermission(token);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (user.isActive() == activate) {
            throw new IllegalStateException("User is already " + (activate ? "active" : "inactive"));
        }

        user.setActive(activate);
        userRepository.save(user);

        return "User " + user.getEmail() + " has been successfully " + (activate ? "activated" : "deactivated");
    }

    public ResponseEntity<?> changeUserActivation(Integer userId, String token, boolean activate) {
        try {
            String message = toggleUserActivation(userId, activate, token);
            User user = userRepository.findById(userId).orElseThrow(); 
            return ResponseEntity.ok().body(Map.of(
                    "success", true,
                    "message", message,
                    "user", new UserDTO(user)
            ));
        } catch (AccessDeniedException | EntityNotFoundException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "error", e.getMessage()
            ));
        }
    }

    public List<UserDTO> getUsersWithFilters(Boolean active, String name) {
        Specification<User> spec = UserSpecification.withFilters(active, name);
        return userRepository.findAll(spec).stream().map(UserDTO::new).toList();
    }
    
}
