package com.matias.gymtracker.controller;

import com.matias.gymtracker.dto.response.WeekLogResponse;
import com.matias.gymtracker.mapper.WeekLogMapper;
import com.matias.gymtracker.service.WeekLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/week-logs")
@RequiredArgsConstructor
public class WeekLogController {

    private final WeekLogService weekLogService;
    private final WeekLogMapper weekLogMapper;

    @GetMapping
    public List<WeekLogResponse> getAllByUser(@RequestParam Long userId) {
        return weekLogMapper.toResponseList(weekLogService.getAllByUser(userId));
    }

    @GetMapping("/{weekLogId}")
    public WeekLogResponse findById(
            @PathVariable Long weekLogId,
            @RequestParam Long userId
    ) {
        return weekLogMapper.toResponse(weekLogService.getById(weekLogId, userId));
    }

    @PostMapping("/current")
    @ResponseStatus(HttpStatus.CREATED)
    public WeekLogResponse findOrCreateCurrentWeek(@RequestParam Long userId) {
        return weekLogMapper.toResponse(weekLogService.findOrCreateCurrentWeek(userId));
    }
}
