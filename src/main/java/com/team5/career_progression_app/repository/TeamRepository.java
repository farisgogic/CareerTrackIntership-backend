package com.team5.career_progression_app.repository;

import com.team5.career_progression_app.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
    @Query("SELECT t.name FROM Team t")
    List<String> getAllTeamNames();


}
