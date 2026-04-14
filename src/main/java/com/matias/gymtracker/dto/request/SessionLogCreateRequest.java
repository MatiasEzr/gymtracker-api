package com.matias.gymtracker.dto.request;

import lombok.Data;

@Data
public class SessionLogCreateRequest {
    private Long routineDayId;
    private String notes;
}
