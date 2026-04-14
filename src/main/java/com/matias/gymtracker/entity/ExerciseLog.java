package com.matias.gymtracker.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exercise_logs")
@Data
@NoArgsConstructor
public class ExerciseLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal weightUsed;

    @Column(nullable = false)
    private Integer setsCompleted;

    @Column(nullable = false)
    private Boolean completedMinimum;

    @Column(nullable = false)
    private Boolean completedFull;

    @ManyToOne
    @JoinColumn(name = "session_log_id", nullable = false)
    private SessionLog sessionLog;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @OneToMany(mappedBy = "exerciseLog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SetLog> sets = new ArrayList<>();

    public ExerciseLog(BigDecimal weightUsed, Integer setsCompleted, Boolean completedMinimum, Boolean completedFull, SessionLog sessionLog, Exercise exercise) {
        this.weightUsed = weightUsed;
        this.setsCompleted = setsCompleted;
        this.completedMinimum = completedMinimum;
        this.completedFull = completedFull;
        this.sessionLog = sessionLog;
        this.exercise = exercise;
    }
}
