package com.team5.career_progression_app.restController;

import com.team5.career_progression_app.core.RoleServiceImpl;
import com.team5.career_progression_app.model.Permission;
import com.team5.career_progression_app.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("role")
public class RoleController {

    private final RoleService roleService;
    private final RoleServiceImpl roleServiceImpl;

    @Autowired
    public RoleController(RoleService roleService, RoleServiceImpl roleServiceImpl) {
        this.roleService = roleService;
        this.roleServiceImpl = roleServiceImpl;
    }
    @GetMapping("/permissions")
    public List<String> getAllPermissions() {
        return roleService.getAllPermissionNames();
    }

    @PostMapping("/selectedPermissions")
    public ResponseEntity<?> getSelectedPermissions(@RequestBody Map<String, Object> request) {
        String inputName = (String) request.get("name");
        List<String> selectedPermissions = (List<String>) request.get("permissions");

        roleService.insertRole(inputName,selectedPermissions);
        //System.out.println("Selected permissions: " + inputName + " " + selectedPermissions);

        return ResponseEntity.ok("Received permissions: ");
    }


}
