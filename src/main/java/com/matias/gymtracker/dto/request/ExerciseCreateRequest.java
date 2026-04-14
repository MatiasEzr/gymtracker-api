package com.matias.gymtracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ExerciseCreateRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String muscle;

    @NotBlank
    private String targetSets;

    @NotBlank
    private String weightKg;
}
