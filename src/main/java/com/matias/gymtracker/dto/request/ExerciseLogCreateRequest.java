package com.matias.gymtracker.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class ExerciseLogCreateRequest {

    @NotNull
    private Long exerciseId;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal weightUsed;

    @NotNull
    @Min(0)
    private Integer setsCompleted;

    @NotNull
    private Boolean completedMinimum;

    @NotNull
    private Boolean completedFull;

    @Valid
    private List<SetLogCreateRequest> sets = new ArrayList<>();
}
