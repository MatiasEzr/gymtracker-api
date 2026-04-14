package com.matias.gymtracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExerciseResponse {
    private Long id;
    private String name;
    private String muscle;
    private String targetSets;
    private String weightKg;
    private Long routineDayId;
}
