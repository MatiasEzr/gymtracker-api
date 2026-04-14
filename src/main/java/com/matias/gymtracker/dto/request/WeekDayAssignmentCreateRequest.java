package com.matias.gymtracker.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.DayOfWeek;

@Data
public class WeekDayAssignmentCreateRequest {

    @NotNull
    private DayOfWeek dayOfWeek;

    @NotNull
    private Long routineDayId;
}
