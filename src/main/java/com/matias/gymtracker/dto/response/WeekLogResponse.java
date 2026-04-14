package com.matias.gymtracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class WeekLogResponse {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer weekNumber;
    private Integer year;
    private Long userId;
    private List<Long> sessionIds;
}
