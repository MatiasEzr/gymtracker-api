package com.matias.gymtracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class SessionLogResponse {
    private Long id;
    private LocalDate date;
    private Boolean completed;
    private String notes;
    private Long weekLogId;
    private Long routineDayId;
    private List<Long> exerciseLogIds;
}
