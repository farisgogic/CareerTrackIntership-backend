package com.team5.career_progression_app.repository;

import com.team5.career_progression_app.model.Status;
import com.team5.career_progression_app.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    Page<Task> findByAssignedToId(Integer userId, Pageable pageable);
    
    List<Task> findByAssignedToId(Integer userId);
    
    List<Task> findByAssignedToIdAndStatus(Integer userId, Status status);
}