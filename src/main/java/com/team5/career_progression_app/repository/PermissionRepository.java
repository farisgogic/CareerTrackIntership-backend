package com.team5.career_progression_app.repository;

import com.team5.career_progression_app.model.Permission;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    Optional<Permission> findByNameIgnoreCase(@Param("name") String name);
}

