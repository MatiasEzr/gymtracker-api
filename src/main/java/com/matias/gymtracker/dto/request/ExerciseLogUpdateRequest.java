package com.matias.gymtracker.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ExerciseLogUpdateRequest {

    @DecimalMin("0.0")
    private BigDecimal weightUsed;

    @Min(0)
    private Integer setsCompleted;

    private Boolean completedMinimum;

    private Boolean completedFull;

    @Valid
    private List<SetLogUpdateRequest> sets;
}
