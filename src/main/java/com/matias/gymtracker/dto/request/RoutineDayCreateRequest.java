package com.matias.gymtracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoutineDayCreateRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String muscleGroup;
}
