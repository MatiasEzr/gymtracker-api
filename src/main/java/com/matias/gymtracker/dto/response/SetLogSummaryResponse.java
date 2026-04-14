package com.matias.gymtracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SetLogSummaryResponse {
    private Long id;
    private Integer setNumber;
    private Integer repsCompleted;
    private Long exerciseLogId;
}
