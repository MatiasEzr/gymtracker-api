package com.matias.gymtracker.controller;

import com.matias.gymtracker.dto.request.ExerciseCreateRequest;
import com.matias.gymtracker.dto.request.ExerciseUpdateRequest;
import com.matias.gymtracker.dto.response.ExerciseResponse;
import com.matias.gymtracker.entity.Exercise;
import com.matias.gymtracker.mapper.ExerciseMapper;
import com.matias.gymtracker.service.ExerciseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercises")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;
    private final ExerciseMapper exerciseMapper;

    @GetMapping
    public List<ExerciseResponse> getAllByRoutineDay(@RequestParam Long routineDayId) {
        return exerciseMapper.toResponseList(exerciseService.getAllByRoutineDay(routineDayId));
    }

    @GetMapping("/{exerciseId}")
    public ExerciseResponse findById(@PathVariable Long exerciseId) {
        return exerciseMapper.toResponse(exerciseService.findByIdOrThrow(exerciseId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExerciseResponse create(
            @RequestParam Long routineDayId,
            @Valid @RequestBody ExerciseCreateRequest request
    ) {
        Exercise created = exerciseService.addToRoutineDay(routineDayId, exerciseMapper.toEntity(request));
        return exerciseMapper.toResponse(created);
    }

    @PatchMapping("/{exerciseId}")
    public ExerciseResponse update(
            @PathVariable Long exerciseId,
            @Valid @RequestBody ExerciseUpdateRequest request
    ) {
        Exercise existing = exerciseService.findByIdOrThrow(exerciseId);
        exerciseMapper.updateEntityFromDto(request, existing);

        Exercise updated = exerciseService.update(exerciseId, existing);
        return exerciseMapper.toResponse(updated);
    }

    @DeleteMapping("/{exerciseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long exerciseId) {
        exerciseService.delete(exerciseId);
    }
}
