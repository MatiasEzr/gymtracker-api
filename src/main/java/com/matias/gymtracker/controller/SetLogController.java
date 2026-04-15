package com.matias.gymtracker.controller;

import com.matias.gymtracker.dto.request.SetLogCreateRequest;
import com.matias.gymtracker.dto.request.SetLogUpdateRequest;
import com.matias.gymtracker.dto.response.SetLogResponse;
import com.matias.gymtracker.entity.SetLog;
import com.matias.gymtracker.mapper.SetLogMapper;
import com.matias.gymtracker.service.SetLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/set-logs")
@RequiredArgsConstructor
public class SetLogController {

    private final SetLogService setLogService;
    private final SetLogMapper setLogMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SetLogResponse create(
            @RequestParam Long exerciseLogId,
            @RequestParam Long userId,
            @Valid @RequestBody SetLogCreateRequest request
    ) {
        SetLog created = setLogService.addToExerciseLog(exerciseLogId, request, userId);
        return setLogMapper.toResponse(created);
    }

    @GetMapping("/{setId}")
    public SetLogResponse findById(
            @PathVariable Long setId,
            @RequestParam Long userId
    ) {
        return setLogMapper.toResponse(setLogService.findById(setId, userId));
    }

    @PatchMapping("/{setId}")
    public SetLogResponse update(
            @PathVariable Long setId,
            @RequestParam Long userId,
            @Valid @RequestBody SetLogUpdateRequest request
    ) {
        return setLogMapper.toResponse(setLogService.update(setId, request, userId));
    }

    @DeleteMapping("/{setId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long setId,
            @RequestParam Long userId
    ) {
        setLogService.delete(setId, userId);
    }
}
