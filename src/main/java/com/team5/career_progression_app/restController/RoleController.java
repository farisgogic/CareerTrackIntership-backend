package com.team5.career_progression_app.restController;

import com.team5.career_progression_app.model.Permission;
import com.team5.career_progression_app.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("role")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }
    @GetMapping("/permissions")
    public List<String> getAllPermissions() {
        return roleService.getAllPermissionNames();
    }
}
