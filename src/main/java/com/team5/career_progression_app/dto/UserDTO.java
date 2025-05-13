package com.team5.career_progression_app.dto;

import com.team5.career_progression_app.model.User;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String roleName;
    private boolean active;
    private String profilePictureUrl;

    public UserDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.roleName = user.getRole() != null ? user.getRole().getName() : null;
        this.active = user.isActive();
        this.profilePictureUrl = user.getProfilePictureUrl();
    }

}
