package com.team5.career_progression_app.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "report_snapshot")
public class ReportSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "report_data")
    private String reportData;

    // Constructors

    public ReportSnapshot() {}

    public ReportSnapshot(String reportData) {
        this.reportData = reportData;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getReportData() {
        return reportData;
    }

    public void setReportData(String reportData) {
        this.reportData = reportData;
    }

    // toString method

    @Override
    public String toString() {
        return "ReportSnapshot{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", reportData='" + reportData + '\'' +
                '}';
    }
}
