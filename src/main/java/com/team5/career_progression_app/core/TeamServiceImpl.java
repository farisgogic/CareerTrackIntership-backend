package com.team5.career_progression_app.core;

import com.team5.career_progression_app.repository.TeamRepository;
import com.team5.career_progression_app.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public List<String> getAllTeamNames() {
        return teamRepository.getAllTeamNames();
    }
}
