package com.matias.gymtracker.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="exercises")
@NoArgsConstructor
@Data
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String muscle;
    private String targetSets;
    private String weightKg;

    @ManyToOne
    @JoinColumn(name = "routine_day_id")
    private RoutineDay routineDay;

}
