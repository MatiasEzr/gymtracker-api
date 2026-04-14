package com.matias.gymtracker.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SetLogCreateRequest {

    @NotNull
    @Min(1)
    private Integer setNumber;

    @NotNull
    @Min(0)
    private Integer repsCompleted;
}
