package com.matias.gymtracker.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WeekLogUpdateRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer weekNumber;
    private Integer year;
}
