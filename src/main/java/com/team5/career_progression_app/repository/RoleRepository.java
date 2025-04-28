package com.team5.career_progression_app.repository;

import com.team5.career_progression_app.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(String name);

    @Query("SELECT DISTINCT r FROM Role r " +
           "LEFT JOIN FETCH r.rolePermissions rp " +
           "LEFT JOIN FETCH rp.permission " +
           "WHERE r.name = :name")
    Optional<Role> findByNameWithPermissions(@Param("name") String name);
}