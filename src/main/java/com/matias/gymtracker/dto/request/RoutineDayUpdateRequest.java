package com.matias.gymtracker.dto.request;

import lombok.Data;

@Data
public class RoutineDayUpdateRequest {
    private String name;
    private String muscleGroup;
}
