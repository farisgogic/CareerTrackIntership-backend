package com.team5.career_progression_app.restController;

import com.team5.career_progression_app.core.RoleServiceImpl;
import com.team5.career_progression_app.dto.CreateRoleRequestDTO;
import com.team5.career_progression_app.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


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
    public Map<String, String> getSelectedPermissions(@RequestBody CreateRoleRequestDTO request) {

        String roleName = request.getRoleName();
        List<String> permissionsNames = request.getPermissionNames();

        roleService.insertRole(roleName, permissionsNames);

        return Map.of("message", "Received permissions for role: " + roleName);
    }

}
