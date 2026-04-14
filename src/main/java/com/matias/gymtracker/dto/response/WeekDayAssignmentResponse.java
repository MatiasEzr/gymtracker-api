package com.matias.gymtracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.DayOfWeek;

@Data
@AllArgsConstructor
public class WeekDayAssignmentResponse {
    private Long id;
    private DayOfWeek dayOfWeek;
    private Long weekTemplateId;
    private Long routineDayId;
}
