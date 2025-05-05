package com.team5.career_progression_app.service;

import com.team5.career_progression_app.exception.DuplicateAssignmentException;
import com.team5.career_progression_app.exception.ResourceNotFoundException;
import com.team5.career_progression_app.model.Permission;
import com.team5.career_progression_app.model.Role;
import com.team5.career_progression_app.model.RolePermission;
import com.team5.career_progression_app.repository.PermissionRepository;
import com.team5.career_progression_app.repository.RolePermissionRepository;
import com.team5.career_progression_app.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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

    public Map<String, Object> getRoleWithPermissions(Integer roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        List<Permission> permissions = role.getRolePermissions().stream()
                .map(RolePermission::getPermission)
                .collect(Collectors.toList());

        return Map.of(
            "role", convertRole(role),
            "permissions", permissions.stream()
                .map(this::convertPermission)
                .collect(Collectors.toList())
        );
    }

    public Map<String, Object> assignPermissionToRole(Integer roleId, Integer permissionId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found"));

        boolean exists = rolePermissionRepository.existsByRoleAndPermission(role, permission);
        if (exists) {
            throw new DuplicateAssignmentException("Permission already assigned to this role");
        }

        RolePermission rolePermission = new RolePermission();
        rolePermission.setRole(role);
        rolePermission.setPermission(permission);
        rolePermissionRepository.save(rolePermission);

        return convertRolePermission(rolePermission);
    }

    public void removePermissionFromRole(Integer id) {
        RolePermission rolePermission = rolePermissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RolePermission not found"));

        rolePermissionRepository.delete(rolePermission);
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
