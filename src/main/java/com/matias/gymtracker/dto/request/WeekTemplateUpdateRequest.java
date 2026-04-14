package com.matias.gymtracker.dto.request;

import lombok.Data;

@Data
public class WeekTemplateUpdateRequest {
    private String name;
    private Boolean isDefault;
}
