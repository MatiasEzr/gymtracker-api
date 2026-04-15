package com.matias.gymtracker.controller;

import com.matias.gymtracker.dto.request.WeekDayAssignmentCreateRequest;
import com.matias.gymtracker.dto.request.WeekTemplateCreateRequest;
import com.matias.gymtracker.dto.response.WeekDayAssignmentResponse;
import com.matias.gymtracker.dto.response.WeekTemplateResponse;
import com.matias.gymtracker.entity.WeekDayAssignment;
import com.matias.gymtracker.entity.WeekTemplate;
import com.matias.gymtracker.mapper.WeekDayAssignmentMapper;
import com.matias.gymtracker.mapper.WeekTemplateMapper;
import com.matias.gymtracker.service.WeekTemplateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/week-templates")
@RequiredArgsConstructor
public class WeekTemplateController {

    private final WeekTemplateService weekTemplateService;
    private final WeekTemplateMapper weekTemplateMapper;
    private final WeekDayAssignmentMapper weekDayAssignmentMapper;

    @GetMapping
    public List<WeekTemplateResponse> getAllByUser(@RequestParam Long userId) {
        return weekTemplateMapper.toResponseList(weekTemplateService.getAllByUser(userId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WeekTemplateResponse create(
            @RequestParam Long userId,
            @Valid @RequestBody WeekTemplateCreateRequest request
    ) {
        WeekTemplate created = weekTemplateService.create(weekTemplateMapper.toEntity(request), userId);
        return weekTemplateMapper.toResponse(created);
    }

    @PostMapping("/{templateId}/days")
    @ResponseStatus(HttpStatus.CREATED)
    public WeekDayAssignmentResponse addDay(
            @PathVariable Long templateId,
            @RequestParam Long userId,
            @Valid @RequestBody WeekDayAssignmentCreateRequest request
    ) {
        WeekDayAssignment created = weekTemplateService.addDay(
                templateId,
                request.getDayOfWeek(),
                request.getRoutineDayId(),
                userId
        );
        return weekDayAssignmentMapper.toResponse(created);
    }

    @DeleteMapping("/days/{assignmentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeDay(
            @PathVariable Long assignmentId,
            @RequestParam Long userId
    ) {
        weekTemplateService.removeDay(assignmentId, userId);
    }

    @PatchMapping("/{templateId}/default")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setAsDefault(
            @PathVariable Long templateId,
            @RequestParam Long userId
    ) {
        weekTemplateService.setAsDefault(templateId, userId);
    }
}
