package com.matias.gymtracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RoutineDayResponse {
    private Long id;
    private String name;
    private String muscleGroup;
    private Long userId;
    private List<Long> exerciseIds;
}
