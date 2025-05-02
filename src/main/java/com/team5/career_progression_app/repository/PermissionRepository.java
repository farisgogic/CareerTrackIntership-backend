package com.team5.career_progression_app.repository;

import com.team5.career_progression_app.model.Permission;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    Optional<Permission> findByNameIgnoreCase(String name);
    @Query(value = """
    SELECT name FROM Permission
""")
    List<String> findAllPermissionNames();

    List<Permission> findByNameIn(List<String> permissionNames);
}

