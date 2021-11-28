package com.vlpz.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne private Task task;
    @ManyToOne private User user;
    private boolean isCompleted;
    private int totalAttempts;
    private long timeSpentAverageInSeconds;
    private long timeSpentOnSuccessfulAttemptInSeconds;
    private long timeSpentTotalInSeconds;
}
