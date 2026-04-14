package com.matias.gymtracker.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "session_logs")
@Data
@NoArgsConstructor
public class SessionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Boolean completed;

    private String notes;

    @ManyToOne
    @JoinColumn(name = "week_log_id", nullable = false)
    private WeekLog weekLog;

    @ManyToOne
    @JoinColumn(name = "routine_day_id", nullable = false)
    private RoutineDay routineDay;

    @OneToMany(mappedBy = "sessionLog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExerciseLog> exerciseLogs = new ArrayList<>();

    public SessionLog(LocalDate date, String notes, WeekLog weekLog, RoutineDay routineDay) {
        this.date = date;
        this.notes = notes;
        this.completed = false;
        this.weekLog = weekLog;
        this.routineDay = routineDay;
    }
}
