package com.team5.career_progression_app.dto;

import java.util.List;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateRoleRequestDTO {

    private String roleName;
    private List<String> permissionNames;

}
