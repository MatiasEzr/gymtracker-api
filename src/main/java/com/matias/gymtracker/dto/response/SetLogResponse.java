package com.matias.gymtracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SetLogResponse {
    private Long id;
    private Integer setNumber;
    private Integer repsCompleted;
}
