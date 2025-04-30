package com.team5.career_progression_app.core;

import com.team5.career_progression_app.model.Role;
import com.team5.career_progression_app.model.Permission;
import com.team5.career_progression_app.model.RolePermission;
import com.team5.career_progression_app.repository.PermissionRepository;
import com.team5.career_progression_app.repository.RolePermissionRepository;
import com.team5.career_progression_app.repository.RoleRepository;
import com.team5.career_progression_app.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    public PermissionRepository permissionRepository;
    public RoleRepository roleRepository;
    public RolePermissionRepository rolePermissionRepository;
    public RoleServiceImpl(PermissionRepository permissionRepository, RoleRepository roleRepository, RolePermissionRepository rolePermissionRepository) {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
    }

    @Override
    public List<String> getAllPermissionNames() {
        return permissionRepository.findAllPermissionNames();
    }

    @Override
    public void insertRole(String name, List<String> permissionNames) {

        Role role = new Role();
        role.setName(name);
        role = roleRepository.save(role);

        List<Permission> permissions = permissionRepository.findByNameIn(permissionNames);
        //role.getPermissions().addAll(permissions)

        for (Permission permission : permissions) {
            RolePermission rp = new RolePermission();
            rp.setRole(role);
            rp.setPermission(permission);
            rolePermissionRepository.save(rp);
        }
    }
}