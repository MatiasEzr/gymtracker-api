package com.matias.gymtracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WeekTemplateResponse {
    private Long id;
    private String name;
    private Boolean isDefault;
    private Long userId;
    private List<Long> dayIds;
}
