package com.team5.career_progression_app.dto;

import java.util.List;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateRoleRequest {

    private String roleName;
    private List<String> permissionNames;

}
