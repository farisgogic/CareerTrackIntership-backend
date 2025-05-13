package com.team5.career_progression_app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.team5.career_progression_app.dto.PositionDTO;
import com.team5.career_progression_app.model.Position;
import com.team5.career_progression_app.repository.PositionRepository;

@Service
public class PositionService {

    private final PositionRepository positionRepository;

    public PositionService(PositionRepository positionRepository){
        this.positionRepository = positionRepository;
    }

    public List<PositionDTO> getAllPositions(){
        List<Position> positions = positionRepository.findAll();

        return positions.stream().map(position -> new PositionDTO(position.getId(), position.getName())).collect(Collectors.toList());
    }
}
