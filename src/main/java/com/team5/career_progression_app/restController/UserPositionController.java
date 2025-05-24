package com.team5.career_progression_app.restController;

import com.team5.career_progression_app.dto.UserPositionDTO;
import com.team5.career_progression_app.service.UserPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/userPositions")
public class UserPositionController {

    @Autowired
    private UserPositionService userPositionService;

    @GetMapping
    public List<UserPositionDTO> getUserPositions(@RequestParam Integer userId) {
        return userPositionService.getUserPositionsForUser(userId);
    }
}