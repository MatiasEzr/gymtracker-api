package com.matias.gymtracker.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;

@Entity
@Table(name = "week_template_days")
@Data
@NoArgsConstructor
public class WeekDayAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek dayOfWeek;

    @ManyToOne
    @JoinColumn(name = "week_template_id", nullable = false)
    private WeekTemplate weekTemplate;

    @ManyToOne
    @JoinColumn(name = "routine_day_id", nullable = false)
    private RoutineDay routineDay;

    public WeekDayAssignment(DayOfWeek dayOfWeek, WeekTemplate weekTemplate, RoutineDay routineDay) {
        this.dayOfWeek = dayOfWeek;
        this.weekTemplate = weekTemplate;
        this.routineDay = routineDay;
    }
}
