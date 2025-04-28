package com.team5.career_progression_app.restController;

import com.team5.career_progression_app.model.Permission;
import com.team5.career_progression_app.model.Role;
import com.team5.career_progression_app.model.RolePermission;
import com.team5.career_progression_app.repository.PermissionRepository;
import com.team5.career_progression_app.repository.RolePermissionRepository;
import com.team5.career_progression_app.repository.RoleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/role-permissions")
public class RolePermissionController {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;

    public RolePermissionController(
            RoleRepository roleRepository,
            PermissionRepository permissionRepository,
            RolePermissionRepository rolePermissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.rolePermissionRepository = rolePermissionRepository;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllRolesAndPermissions() {
        List<Role> roles = roleRepository.findAll();
        List<Permission> permissions = permissionRepository.findAll();
        List<RolePermission> rolePermissions = rolePermissionRepository.findAll();

        return ResponseEntity.ok(Map.of(
            "roles", roles.stream()
                .map(this::convertRole)
                .collect(Collectors.toList()),
            "permissions", permissions.stream()
                .map(this::convertPermission)
                .collect(Collectors.toList()),
            "rolePermissions", rolePermissions.stream()
                .map(this::convertRolePermission)
                .collect(Collectors.toList())
        ));
    }

    @GetMapping("/roles/{roleId}")
    public ResponseEntity<?> getRoleWithPermissions(@PathVariable Integer roleId) {
        return roleRepository.findById(roleId)
            .map(role -> {
                List<Permission> permissions = role.getRolePermissions().stream()
                    .map(RolePermission::getPermission)
                    .collect(Collectors.toList());
                
                return ResponseEntity.ok(Map.of(
                    "role", convertRole(role),
                    "permissions", permissions.stream()
                        .map(this::convertPermission)
                        .collect(Collectors.toList())
                ));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> assignPermissionToRole(
            @RequestParam Integer roleId,
            @RequestParam Integer permissionId) {
        
        Role role = roleRepository.findById(roleId)
            .orElseThrow(() -> new RuntimeException("Role not found"));
        Permission permission = permissionRepository.findById(permissionId)
            .orElseThrow(() -> new RuntimeException("Permission not found"));

        boolean exists = rolePermissionRepository.existsByRoleAndPermission(role, permission);
        if (exists) {
            return ResponseEntity.badRequest().body("Permission already assigned to this role");
        }

        RolePermission rolePermission = new RolePermission();
        rolePermission.setRole(role);
        rolePermission.setPermission(permission);
        rolePermissionRepository.save(rolePermission);

        return ResponseEntity.ok(convertRolePermission(rolePermission));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removePermissionFromRole(@PathVariable Integer id) {
        return rolePermissionRepository.findById(id)
            .map(rolePermission -> {
                rolePermissionRepository.delete(rolePermission);
                return ResponseEntity.ok().build();
            })
            .orElse(ResponseEntity.notFound().build());
    }

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