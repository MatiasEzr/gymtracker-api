package com.matias.gymtracker.dto.request;

import lombok.Data;

@Data
public class ExerciseUpdateRequest {
    private String name;
    private String muscle;
    private String targetSets;
    private String weightKg;
}
