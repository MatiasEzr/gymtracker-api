package com.matias.gymtracker.controller;

import com.matias.gymtracker.dto.request.SessionLogCreateRequest;
import com.matias.gymtracker.dto.response.SessionLogResponse;
import com.matias.gymtracker.entity.SessionLog;
import com.matias.gymtracker.mapper.SessionLogMapper;
import com.matias.gymtracker.service.SessionLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sessions")
@RequiredArgsConstructor
public class SessionLogController {

    private final SessionLogService sessionLogService;
    private final SessionLogMapper sessionLogMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SessionLogResponse create(
            @RequestParam Long userId,
            @Valid @RequestBody SessionLogCreateRequest request
    ) {
        SessionLog created = sessionLogService.create(request.getRoutineDayId(), request.getNotes(), userId);
        return sessionLogMapper.toResponse(created);
    }

    @GetMapping("/{sessionId}")
    public SessionLogResponse findById(
            @PathVariable Long sessionId,
            @RequestParam Long userId
    ) {
        return sessionLogMapper.toResponse(sessionLogService.getById(sessionId, userId));
    }

    @PatchMapping("/{sessionId}/complete")
    public SessionLogResponse complete(
            @PathVariable Long sessionId,
            @RequestParam Long userId
    ) {
        return sessionLogMapper.toResponse(sessionLogService.complete(sessionId, userId));
    }

    @PostMapping("/from-template/{templateId}")
    @ResponseStatus(HttpStatus.CREATED)
    public List<SessionLogResponse> createFromTemplate(
            @PathVariable Long templateId,
            @RequestParam Long userId
    ) {
        return sessionLogMapper.toResponseList(sessionLogService.createFromTemplate(templateId, userId));
    }
}
