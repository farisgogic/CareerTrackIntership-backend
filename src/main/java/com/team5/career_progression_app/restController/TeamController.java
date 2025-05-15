package com.team5.career_progression_app.restController;

import com.team5.career_progression_app.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/names")
    public ResponseEntity<List<String>> getAllTeamNames() {
        List<String> names = teamService.getAllTeamNames();
        return ResponseEntity.ok(names);
    }
}


