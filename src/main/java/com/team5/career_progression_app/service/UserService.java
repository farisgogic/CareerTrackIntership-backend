package com.team5.career_progression_app.service;

import com.team5.career_progression_app.dto.ApiResponse;
import com.team5.career_progression_app.dto.UserDTO;
import com.team5.career_progression_app.exception.AccessDeniedException;
import com.team5.career_progression_app.exception.ResourceNotFoundException;
import com.team5.career_progression_app.model.Permission;
import com.team5.career_progression_app.model.Role;
import com.team5.career_progression_app.model.User;
import com.team5.career_progression_app.repository.PermissionRepository;
import com.team5.career_progression_app.repository.RoleRepository;
import com.team5.career_progression_app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, JwtService jwtService,
                       PermissionRepository permissionRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserDTO::new)
                .toList();
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

    private void checkManageUsersPermission(String token) {
        Permission manageUsersPermission = permissionRepository.findByNameIgnoreCase("MANAGE_USERS")
                .orElseThrow(() -> new ResourceNotFoundException("Permission 'MANAGE_USERS' not found"));

        List<Integer> userPermissionIds = jwtService.extractPermissionIds(token);

        if (!userPermissionIds.contains(manageUsersPermission.getId())) {
            throw new AccessDeniedException("Access Denied - Permission 'MANAGE_USERS' is required");
        }
    }

    private String toggleUserActivation(Integer userId, boolean activate, String token) {
        checkManageUsersPermission(token);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setActive(activate);
        userRepository.save(user);

        return "User " + user.getEmail() + " has been successfully " + (activate ? "activated" : "deactivated");
    }

    public ApiResponse<UserDTO> changeUserActivation(Integer userId, String token, boolean activate) {
        String message = toggleUserActivation(userId, activate, token);
        User user = userRepository.findById(userId).orElseThrow();
        return new ApiResponse<>(true, message, new UserDTO(user));
    }


    public List<UserDTO> getUsersWithFilters(Boolean active, String name) {
        List<User> filteredUsers = userRepository.filterUsers(active, name);
        return filteredUsers.stream()
                .map(UserDTO::new)
                .toList();
    }

    public void updateUserRole(Integer userId, String roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));

        user.setRole(role);
        userRepository.save(user);
    }

    public void deleteUserRoleAndDeactivate(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role defaultRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Default USER role not found"));

        user.setRole(defaultRole);
        user.setActive(false);
        userRepository.save(user);
    }
}
