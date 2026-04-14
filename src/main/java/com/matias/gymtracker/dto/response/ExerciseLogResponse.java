package com.matias.gymtracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class ExerciseLogResponse {
    private Long id;
    private BigDecimal weightUsed;
    private Integer setsCompleted;
    private Boolean completedMinimum;
    private Boolean completedFull;
    private Long sessionId;
    private Long exerciseId;
    private String exerciseName;
    private List<SetLogResponse> sets;
}
