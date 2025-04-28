package com.team5.career_progression_app.service;

import com.team5.career_progression_app.model.Permission;
import com.team5.career_progression_app.model.Role;
import com.team5.career_progression_app.model.RolePermission;
import com.team5.career_progression_app.repository.PermissionRepository;
import com.team5.career_progression_app.repository.RolePermissionRepository;
import com.team5.career_progression_app.repository.RoleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RolePermissionService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;

    public RolePermissionService(
            RoleRepository roleRepository,
            PermissionRepository permissionRepository,
            RolePermissionRepository rolePermissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.rolePermissionRepository = rolePermissionRepository;
    }

    public Map<String, Object> getAllRolesAndPermissions() {
        List<Role> roles = roleRepository.findAll();
        List<Permission> permissions = permissionRepository.findAll();
        List<RolePermission> rolePermissions = rolePermissionRepository.findAll();

        return Map.of(
            "roles", roles.stream()
                .map(this::convertRole)
                .collect(Collectors.toList()),
            "permissions", permissions.stream()
                .map(this::convertPermission)
                .collect(Collectors.toList()),
            "rolePermissions", rolePermissions.stream()
                .map(this::convertRolePermission)
                .collect(Collectors.toList())
        );
    }

    public ResponseEntity<?> getRoleWithPermissions(Integer roleId) {
        Optional<Role> roleOptional = roleRepository.findById(roleId);

        if (roleOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Role role = roleOptional.get();
        List<Permission> permissions = role.getRolePermissions().stream()
                .map(RolePermission::getPermission)
                .collect(Collectors.toList());

        return ResponseEntity.ok(Map.of(
            "role", convertRole(role),
            "permissions", permissions.stream()
                .map(this::convertPermission)
                .collect(Collectors.toList())
        ));
    }

    public ResponseEntity<?> assignPermissionToRole(Integer roleId, Integer permissionId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new RuntimeException("Permission not found"));

        boolean exists = rolePermissionRepository.existsByRoleAndPermission(role, permission);
        if (exists) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "duplicate_assignment",
                "message", "Permission already assigned to this role"
            ));
        }

        RolePermission rolePermission = new RolePermission();
        rolePermission.setRole(role);
        rolePermission.setPermission(permission);
        rolePermissionRepository.save(rolePermission);

        return ResponseEntity.ok(convertRolePermission(rolePermission));
    }

    public ResponseEntity<?> removePermissionFromRole(Integer id) {
        return rolePermissionRepository.findById(id)
                .map(rolePermission -> {
                    rolePermissionRepository.delete(rolePermission);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Conversion Methods (Private)
    private Map<String, Object> convertRole(Role role) {
        return Map.of(
            "id", role.getId(),
            "name", role.getName()
        );
    }

    private Map<String, Object> convertPermission(Permission permission) {
        return Map.of(
            "id", permission.getId(),
            "name", permission.getName()
        );
    }

    private Map<String, Object> convertRolePermission(RolePermission rolePermission) {
        return Map.of(
            "id", rolePermission.getId(),
            "role", convertRole(rolePermission.getRole()),
            "permission", convertPermission(rolePermission.getPermission())
        );
    }
}
