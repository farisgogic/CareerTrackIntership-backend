package com.team5.career_progression_app.restController;

import com.team5.career_progression_app.core.RoleServiceImpl;
import com.team5.career_progression_app.dto.CreateRoleRequest;
import com.team5.career_progression_app.model.Permission;
import com.team5.career_progression_app.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("role")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService, RoleServiceImpl roleServiceImpl) {
        this.roleService = roleService;
    }

    @GetMapping("/permissions")
    public List<String> getAllPermissions() {
        return roleService.getAllPermissionNames();
    }

    @PostMapping("/selectedPermissions")
    public ResponseEntity<?> getSelectedPermissions(@RequestBody CreateRoleRequest request) {

        String roleName = request.getRoleName();
        List<String> permissionsNames = request.getPermissionNames();

        roleService.insertRole(roleName, permissionsNames);

        return ResponseEntity.ok("Received permissions: ");
    }

}
