package com.team5.career_progression_app.core;

import com.team5.career_progression_app.model.Permission;
import com.team5.career_progression_app.repository.PermissionRepository;
import com.team5.career_progression_app.repository.RoleRepository;
import com.team5.career_progression_app.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    public PermissionRepository permissionRepository;

    public RoleServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public List<String> getAllPermissionNames() {
        return permissionRepository.findAllPermissionNames();
    }
}