package com.team5.career_progression_app.testing.core;

import com.team5.career_progression_app.model.Permission;
import com.team5.career_progression_app.model.Role;
import com.team5.career_progression_app.repository.PermissionRepository;
import com.team5.career_progression_app.repository.RolePermissionRepository;
import com.team5.career_progression_app.repository.RoleRepository;
import com.team5.career_progression_app.service.RoleService;
import com.team5.career_progression_app.testing.SpringBootTestWithPostgres;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTestWithPostgres
public class RoleServiceImplTest {

    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Test
    void givenANewRole_whenCreatingANewRole_thenShouldCreteRole() {

        String roleName = "MODERATOR";

        Role createdRole = roleService.insertRole(roleName, Collections.emptyList());

        assertThat(createdRole).isNotNull();
        assertThat(createdRole.getId()).isNotNull();
        assertThat(createdRole.getName()).isEqualTo(roleName);

        Optional<Role> fromDb = roleRepository.findById(createdRole.getId());
        assertThat(fromDb).isPresent();
        assertThat(fromDb.get().getName()).isEqualTo(roleName);
    }

    @Test
    void givenExistingRole_whenUpdatingName_thenNameShouldBeUpdated() {

        Role role = roleService.insertRole("TEMP", Collections.emptyList());

        role.setName("UPDATED_ROLE");
        Role updated = roleRepository.save(role);

        assertThat(updated.getName()).isEqualTo("UPDATED_ROLE");
    }

    @Test
    void givenExistingRole_whenDeleting_thenRoleShouldBeRemoved() {

        Role role = roleService.insertRole("TO_DELETE", Collections.emptyList());
        Integer id = role.getId();

        roleRepository.delete(role);

        assertThat(roleRepository.findById(id)).isNotPresent();
    }

    @Test
    void givenNullRoleName_whenInsertingRole_thenExceptionShouldBeThrown() {
        assertThrows(DataIntegrityViolationException.class, () -> {
            roleService.insertRole(null, Collections.emptyList());
        });
    }

}
