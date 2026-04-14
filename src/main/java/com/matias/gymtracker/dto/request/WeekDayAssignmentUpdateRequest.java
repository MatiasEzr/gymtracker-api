package com.matias.gymtracker.dto.request;

import lombok.Data;

import java.time.DayOfWeek;

@Data
public class WeekDayAssignmentUpdateRequest {
    private DayOfWeek dayOfWeek;
    private Long routineDayId;
}
