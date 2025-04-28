package com.team5.career_progression_app.restController;

import com.team5.career_progression_app.service.RolePermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/role-permissions")
public class RolePermissionController {

    private final RolePermissionService rolePermissionService;

    public RolePermissionController(RolePermissionService rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllRolesAndPermissions() {
        return ResponseEntity.ok(rolePermissionService.getAllRolesAndPermissions());
    }

    @GetMapping("/roles/{roleId}")
    public ResponseEntity<?> getRoleWithPermissions(@PathVariable Integer roleId) {
        return rolePermissionService.getRoleWithPermissions(roleId);
    }

    @PostMapping
    public ResponseEntity<?> assignPermissionToRole(
            @RequestParam Integer roleId,
            @RequestParam Integer permissionId) {
        return rolePermissionService.assignPermissionToRole(roleId, permissionId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removePermissionFromRole(@PathVariable Integer id) {
        return rolePermissionService.removePermissionFromRole(id);
    }
}
