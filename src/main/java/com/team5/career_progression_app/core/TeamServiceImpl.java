package com.team5.career_progression_app.core;

import com.team5.career_progression_app.dto.TeamDTO;
import com.team5.career_progression_app.repository.TeamRepository;
import com.team5.career_progression_app.service.TeamService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public List<TeamDTO> getTeams() {
        return teamRepository.findAll().stream()
                .map(TeamDTO::new)
                .toList();
    }

    @Override
    public List<String> getAllTeamNames() {
        return teamRepository.getAllTeamNames();
    }
} 

