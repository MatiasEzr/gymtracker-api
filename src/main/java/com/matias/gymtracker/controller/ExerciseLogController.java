package com.matias.gymtracker.controller;

import com.matias.gymtracker.dto.request.ExerciseLogCreateRequest;
import com.matias.gymtracker.dto.request.ExerciseLogUpdateRequest;
import com.matias.gymtracker.dto.response.ExerciseLogResponse;
import com.matias.gymtracker.entity.ExerciseLog;
import com.matias.gymtracker.mapper.ExerciseLogMapper;
import com.matias.gymtracker.service.ExerciseLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exercise-logs")
@RequiredArgsConstructor
public class ExerciseLogController {

    private final ExerciseLogService exerciseLogService;
    private final ExerciseLogMapper exerciseLogMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExerciseLogResponse addToSession(
            @RequestParam Long sessionId,
            @RequestParam Long userId,
            @Valid @RequestBody ExerciseLogCreateRequest request
    )
    {
        ExerciseLog created = exerciseLogService.addToSession(
                sessionId,
                exerciseLogMapper.toEntity(request),
                userId
        );

        return exerciseLogMapper.toResponse(created);
    }

    @PatchMapping("/{exerciseLogId}")
    public ExerciseLogResponse update(
            @PathVariable Long exerciseLogId,
            @RequestParam Long userId,
            @Valid @RequestBody ExerciseLogUpdateRequest request
    ) {
        ExerciseLog updated = exerciseLogService.update(
                exerciseLogId,
                exerciseLogMapper.toEntity(request),
                userId
        );

        return exerciseLogMapper.toResponse(updated);
    }

    @GetMapping("/{exerciseLogId}")
    public ExerciseLogResponse findById(
            @PathVariable Long exerciseLogId,
            @RequestParam Long userId
    ) {
        return exerciseLogMapper.toResponse(exerciseLogService.findById(exerciseLogId, userId));
    }

    @DeleteMapping("/{exerciseLogId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long exerciseLogId,
            @RequestParam Long userId
    ) {
        exerciseLogService.delete(exerciseLogId, userId);
    }
}
