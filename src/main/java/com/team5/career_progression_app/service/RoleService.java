package com.team5.career_progression_app.service;

import java.util.List;

public interface RoleService {

    List<String> getAllPermissionNames();
    void insertRole(String roleName, List<String> permissionNames);
}
