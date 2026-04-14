package com.matias.gymtracker.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class SetLogUpdateRequest {

    @Min(1)
    private Integer setNumber;

    @Min(0)
    private Integer repsCompleted;
}
