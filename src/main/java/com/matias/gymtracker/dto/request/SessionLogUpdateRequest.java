package com.matias.gymtracker.dto.request;

import lombok.Data;

@Data
public class SessionLogUpdateRequest {
    private String notes;
    private Boolean completed;
}
