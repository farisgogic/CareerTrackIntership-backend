package com.team5.career_progression_app.dto;

import com.team5.career_progression_app.model.User;

import lombok.Getter;

@Getter
public class UserDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String roleName;

    public UserDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.roleName = user.getRole() != null ? user.getRole().getName() : null;
    }

}
