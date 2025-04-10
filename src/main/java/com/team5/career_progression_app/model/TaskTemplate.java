package com.team5.career_progression_app.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "task_template")
public class TaskTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "task_requirements")
    private String taskRequirements;

    // Relationships

    @OneToMany(mappedBy = "template")
    private List<Task> tasks;

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTaskRequirements() {
        return taskRequirements;
    }

    public void setTaskRequirements(String taskRequirements) {
        this.taskRequirements = taskRequirements;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    // toString method

    @Override
    public String toString() {
        return "TaskTemplate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", taskRequirements='" + taskRequirements + '\'' +
                '}';
    }
}