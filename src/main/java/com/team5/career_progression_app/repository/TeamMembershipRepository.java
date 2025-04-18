package com.team5.career_progression_app.repository;

import com.team5.career_progression_app.model.TeamMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamMembershipRepository extends JpaRepository<TeamMembership, Integer> {
}
