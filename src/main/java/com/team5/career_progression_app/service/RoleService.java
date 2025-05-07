package com.team5.career_progression_app.service;

import com.team5.career_progression_app.model.Role;

import java.util.List;

public interface RoleService {

    List<String> getAllPermissionNames();
    Role insertRole(String roleName, List<String> permissionNames);
}
