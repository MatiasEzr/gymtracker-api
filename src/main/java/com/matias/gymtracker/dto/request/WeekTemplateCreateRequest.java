package com.matias.gymtracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WeekTemplateCreateRequest {

    @NotBlank
    private String name;
}
