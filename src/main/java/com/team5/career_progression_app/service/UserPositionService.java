package com.team5.career_progression_app.service;

import com.team5.career_progression_app.dto.UserPositionDTO;
import com.team5.career_progression_app.model.PositionLevel;
import com.team5.career_progression_app.model.UserPosition;
import com.team5.career_progression_app.repository.PositionLevelRepository;
import com.team5.career_progression_app.repository.UserPositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserPositionService {

    @Autowired
    private UserPositionRepository userPositionRepository;

    @Autowired
    private PositionLevelRepository positionLevelRepository;

    public List<UserPositionDTO> getUserPositionsForUser(Integer userId) {
        List<UserPosition> userPositions = userPositionRepository.findByUserId(userId); 

        return userPositions.stream().map(userPosition -> {
            Integer currentLevel = userPosition.getPositionLevel().getLevel();
            Integer nextLevel = currentLevel + 1;
            String description = userPosition.getPositionLevel().getDescription();
            String nextDescription = null;
            PositionLevel nextPositionLevel = positionLevelRepository.findByLevelAndPositionId(nextLevel, userPosition.getPosition().getId()).orElse(null);
            if (nextPositionLevel != null) {
                nextDescription = nextPositionLevel.getDescription();
            }
            return new UserPositionDTO(
                userPosition.getUser().getId(),
                userPosition.getPosition().getId(),
                currentLevel,
                nextLevel,
                description,
                nextDescription
            );
        }).collect(Collectors.toList());
    }
}
