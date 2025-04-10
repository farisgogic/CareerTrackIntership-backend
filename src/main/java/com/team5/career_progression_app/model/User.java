package com.team5.career_progression_app.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    // Relationships

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;

    @OneToMany(mappedBy = "recipient")
    private List<OnDemandReport> receivedReports;

    @OneToMany(mappedBy = "recipient")
    private List<Notification> notifications;

    @OneToMany(mappedBy = "user")
    private List<PromotionRequest> promotionRequests;

    @OneToMany(mappedBy = "lead")
    private List<Team> teams;

    @OneToMany(mappedBy = "user")
    private List<TeamMembership> teamMemberships;

    @OneToMany(mappedBy = "user")
    private List<UserPosition> userPositions;

    @OneToMany(mappedBy = "assignedTo")
    private List<Task> assignedTasks;

    @OneToMany(mappedBy = "user")
    private List<TaskComment> comments;

    @OneToMany(mappedBy = "author")
    private List<TaskComment> taskComments;

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<OnDemandReport> getReceivedReports() {
        return receivedReports;
    }

    public void setReceivedReports(List<OnDemandReport> receivedReports) {
        this.receivedReports = receivedReports;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public List<PromotionRequest> getPromotionRequests() {
        return promotionRequests;
    }

    public void setPromotionRequests(List<PromotionRequest> promotionRequests) {
        this.promotionRequests = promotionRequests;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public List<TeamMembership> getTeamMemberships() {
        return teamMemberships;
    }

    public void setTeamMemberships(List<TeamMembership> teamMemberships) {
        this.teamMemberships = teamMemberships;
    }

    public List<UserPosition> getUserPositions() {
        return userPositions;
    }

    public void setUserPositions(List<UserPosition> userPositions) {
        this.userPositions = userPositions;
    }

    public List<Task> getAssignedTasks() {
        return assignedTasks;
    }

    public void setAssignedTasks(List<Task> assignedTasks) {
        this.assignedTasks = assignedTasks;
    }

    public List<TaskComment> getComments() {
        return comments;
    }

    public void setComments(List<TaskComment> comments) {
        this.comments = comments;
    }

    public List<TaskComment> getTaskComments() {
        return taskComments;
    }

    public void setTaskComments(List<TaskComment> taskComments) {
        this.taskComments = taskComments;
    }

    // toString method

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
