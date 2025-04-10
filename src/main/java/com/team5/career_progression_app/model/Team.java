package com.team5.career_progression_app.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    // Relationships

    @ManyToOne
    @JoinColumn(name = "lead_id")
    private User lead;

    @OneToMany(mappedBy = "team")
    private List<TeamMembership> memberships;

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

    public User getLead() {
        return lead;
    }

    public void setLead(User lead) {
        this.lead = lead;
    }

    public List<TeamMembership> getMemberships() {
        return memberships;
    }

    public void setMemberships(List<TeamMembership> memberships) {
        this.memberships = memberships;
    }

    // toString method

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", leadId=" + lead.getId() +
                '}';
    }
}
