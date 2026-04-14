package com.matias.gymtracker.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "set_logs")
@Data
@NoArgsConstructor
public class SetLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int setNumber;

    @Column(nullable = false)
    private int repsCompleted;

    @ManyToOne
    @JoinColumn(name = "exercise_log_id", nullable = false)
    private ExerciseLog exerciseLog;

    public SetLog(int setNumber, int repsCompleted, ExerciseLog exerciseLog) {
        this.setNumber = setNumber;
        this.repsCompleted = repsCompleted;
        this.exerciseLog = exerciseLog;
    }
}