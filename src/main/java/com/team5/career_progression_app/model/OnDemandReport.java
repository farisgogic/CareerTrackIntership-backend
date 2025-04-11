package com.team5.career_progression_app.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "on_demand_report")
public class OnDemandReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "data")
    private String data;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Relationships

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private User recipient;

    // Constructors

    public OnDemandReport() {}

    public OnDemandReport(String data, User recipient) {
        this.data = data;
        this.recipient = recipient;
    }

    // @PrePersist

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    // toString method

    @Override
    public String toString() {
        return "OnDemandReport{" +
                "id=" + id +
                ", data='" + data + '\'' +
                ", createdAt=" + createdAt +
                ", recipientId=" + recipient.getId() +
                '}';
    }
}
