package com.team5.career_progression_app.core;

import com.team5.career_progression_app.model.Role;
import com.team5.career_progression_app.model.Permission;
import com.team5.career_progression_app.model.RolePermission;
import com.team5.career_progression_app.repository.PermissionRepository;
import com.team5.career_progression_app.repository.RolePermissionRepository;
import com.team5.career_progression_app.repository.RoleRepository;
import com.team5.career_progression_app.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    public RoleRepository roleRepository;
    public PermissionRepository permissionRepository;
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

    @Transactional
    @Override
    public void insertRole(String roleName, List<String> permissionNames) {

        Role role = new Role();
        role.setName(roleName);
        role = roleRepository.save(role);

        List<Permission> permissions = permissionRepository.findByNameIn(permissionNames);

        for (Permission permission : permissions) {
            RolePermission rp = new RolePermission();
            rp.setRole(role);
            rp.setPermission(permission);
            rolePermissionRepository.save(rp);
        }
    }
}