package com.team5.career_progression_app.service;

import com.team5.career_progression_app.model.Permission;
import com.team5.career_progression_app.model.User;
import com.team5.career_progression_app.repository.PermissionRepository;
import com.team5.career_progression_app.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, JwtService jwtService, PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.permissionRepository = permissionRepository;
    }
    public List<User> getInactiveUsers() {
        return userRepository.findByActiveFalse();
    }

    public String activateUser(Integer userId, String token) throws AccessDeniedException {
        Permission manageUsersPermission = permissionRepository.findByNameIgnoreCase("MANAGE_USERS")
            .orElseThrow(() -> new IllegalStateException("Dozvola 'MANAGE_USERS' nije pronađena u bazi"));
        
        if (!jwtService.hasPermissionId(token, manageUsersPermission.getId())) {
            throw new AccessDeniedException("Neovlašten pristup - Potrebna dozvola: MANAGE_USERS");
        }
    
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Korisnik nije pronađen"));
    
        if (user.isActive()) {
            throw new IllegalStateException("Korisnik je već aktivan");
        }
    
        user.setActive(true);
        userRepository.save(user);
        return "Korisnik " + user.getEmail() + " je uspješno aktiviran";
    }

    public String getProfilePictureFromToken(String token) {
        if (token == null) return null;
        return jwtService.extractClaim(token, claims -> claims.get("picture", String.class));
    }
}
