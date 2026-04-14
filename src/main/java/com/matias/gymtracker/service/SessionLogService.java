package com.matias.gymtracker.service;

import com.matias.gymtracker.entity.*;
import com.matias.gymtracker.exceptions.ForbiddenException;
import com.matias.gymtracker.exceptions.ResourceNotFoundException;
import com.matias.gymtracker.exceptions.SessionAlreadyExistsException;
import com.matias.gymtracker.repository.RoutineDayRepository;
import com.matias.gymtracker.repository.SessionLogRepository;
import com.matias.gymtracker.repository.WeekTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionLogService {

    private final SessionLogRepository sessionLogRepository;
    private final WeekLogService weekLogService;
    private final WeekTemplateRepository weekTemplateRepository;
    private final RoutineDayRepository routineDayRepository;

    /**
     * Create a session for today
     */
    public SessionLog create(Long routineDayId, String notes, Long userId) {

        WeekLog week = weekLogService.findOrCreateCurrentWeek(userId);

        ZoneId zone = ZoneId.of("America/Argentina/Buenos_Aires");
        LocalDate today = LocalDate.now(zone);

        // validate duplicate session for the day
        if (sessionLogRepository.existsByWeekLogIdAndDate(week.getId(), today)) {
            throw new SessionAlreadyExistsException("Session already exists for today");
        }

        RoutineDay routineDay = routineDayRepository.findById(routineDayId)
                .orElseThrow(() -> new ResourceNotFoundException("RoutineDay", routineDayId));

        SessionLog session = new SessionLog(today, notes, week, routineDay);

        // maintain relationship
        week.getSessions().add(session);

        return sessionLogRepository.save(session);
    }

    /**
     * Get session with exercise logs
     */
    public SessionLog getById(Long sessionId, Long userId) {

        SessionLog session = sessionLogRepository.findByIdWithExercises(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("SessionLog", sessionId));

        // validate ownership
        if (!session.getWeekLog().getUser().getId().equals(userId)) {
            throw new ForbiddenException("You do not have access to this session");
        }

        return session;
    }

    /**
     * Mark session as completed
     */
    public SessionLog complete(Long sessionId, Long userId) {

        SessionLog session = sessionLogRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("SessionLog", sessionId));

        if (!session.getWeekLog().getUser().getId().equals(userId)) {
            throw new ForbiddenException("You do not have access to this session");
        }

        session.setCompleted(true);

        return sessionLogRepository.save(session);
    }

    /**
     * Create sessions for a week based on template
     */
    public List<SessionLog> createFromTemplate(Long templateId, Long userId) {

        WeekTemplate template = weekTemplateRepository.findById(templateId)
                .orElseThrow(() -> new ResourceNotFoundException("WeekTemplate", templateId));

        if (!template.getUser().getId().equals(userId)) {
            throw new ForbiddenException("You do not have access to this template");
        }

        WeekLog week = weekLogService.findOrCreateCurrentWeek(userId);

        ZoneId zone = ZoneId.of("America/Argentina/Buenos_Aires");
        LocalDate monday = LocalDate.now(zone)
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        List<SessionLog> createdSessions = new ArrayList<>();

        for (WeekDayAssignment assignment : template.getDays()) {

            LocalDate sessionDate = monday.plusDays(
                    assignment.getDayOfWeek().getValue() - 1
            );

            // avoid duplicates
            if (sessionLogRepository.existsByWeekLogIdAndDate(week.getId(), sessionDate)) {
                continue; // skip existing
            }

            SessionLog session = new SessionLog(
                    sessionDate,
                    null,
                    week,
                    assignment.getRoutineDay()
            );

            week.getSessions().add(session);
            createdSessions.add(session);
        }

        return sessionLogRepository.saveAll(createdSessions);
    }
}