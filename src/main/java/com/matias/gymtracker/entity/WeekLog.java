package com.matias.gymtracker.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "week_logs")
@Data
@NoArgsConstructor
public class WeekLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private int weekNumber;

    @Column(name = "log_year", nullable = false)
    private int year;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "weekLog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SessionLog> sessions = new ArrayList<>();

    public WeekLog(LocalDate startDate, LocalDate endDate, int weekNumber, int year, User user) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.weekNumber = weekNumber;
        this.year = year;
        this.user = user;
    }
}
