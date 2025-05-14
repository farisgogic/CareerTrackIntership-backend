package com.team5.career_progression_app.restController;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team5.career_progression_app.dto.PositionDTO;
import com.team5.career_progression_app.service.PositionService;

@RestController
@RequestMapping("/api/positions")
public class PositionController {
    private final PositionService positionService;

    public PositionController(PositionService positionService){
        this.positionService = positionService;
    }

    @GetMapping
    public List<PositionDTO> getAllPositions(){
        return positionService.getAllPositions();
    }
}
