package com.matias.gymtracker.controller;

import com.matias.gymtracker.dto.request.RoutineDayCreateRequest;
import com.matias.gymtracker.dto.request.RoutineDayUpdateRequest;
import com.matias.gymtracker.dto.response.RoutineDayResponse;
import com.matias.gymtracker.entity.RoutineDay;
import com.matias.gymtracker.mapper.RoutineDayMapper;
import com.matias.gymtracker.service.RoutineDayService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routine-days")
@RequiredArgsConstructor
public class RoutineDayController {

    private final RoutineDayService routineDayService;
    private final RoutineDayMapper routineDayMapper;

    @GetMapping
    public List<RoutineDayResponse> getAllByUser(@RequestParam Long userId) {
        return routineDayMapper.toResponseList(routineDayService.getAllByUser(userId));
    }

    @GetMapping("/{routineDayId}")
    public RoutineDayResponse findById(
            @PathVariable Long routineDayId,
            @RequestParam Long userId
    ) {
        return routineDayMapper.toResponse(routineDayService.findByIdOrThrow(routineDayId, userId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoutineDayResponse create(
            @RequestParam Long userId,
            @Valid @RequestBody RoutineDayCreateRequest request
    ) {
        RoutineDay created = routineDayService.create(routineDayMapper.toEntity(request), userId);
        return routineDayMapper.toResponse(created);
    }

    @PatchMapping("/{routineDayId}")
    public RoutineDayResponse update(
            @PathVariable Long routineDayId,
            @RequestParam Long userId,
            @Valid @RequestBody RoutineDayUpdateRequest request
    ) {
        RoutineDay existing = routineDayService.findByIdOrThrow(routineDayId, userId);
        routineDayMapper.updateEntityFromDto(request, existing);

        RoutineDay updated = routineDayService.update(routineDayId, existing, userId);
        return routineDayMapper.toResponse(updated);
    }

    @DeleteMapping("/{routineDayId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long routineDayId,
            @RequestParam Long userId
    ) {
        routineDayService.delete(routineDayId, userId);
    }
}
