package com.matias.gymtracker.service;

import com.matias.gymtracker.entity.RoutineDay;
import com.matias.gymtracker.entity.User;
import com.matias.gymtracker.entity.WeekDayAssignment;
import com.matias.gymtracker.entity.WeekTemplate;
import com.matias.gymtracker.exceptions.DayAlreadyAssignedException;
import com.matias.gymtracker.exceptions.DuplicateResourceException;
import com.matias.gymtracker.exceptions.ForbiddenException;
import com.matias.gymtracker.exceptions.ResourceNotFoundException;
import com.matias.gymtracker.repository.RoutineDayRepository;
import com.matias.gymtracker.repository.UserRepository;
import com.matias.gymtracker.repository.WeekDayAssignmentRepository;
import com.matias.gymtracker.repository.WeekTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeekTemplateService {

    private final WeekTemplateRepository weekTemplateRepository;
    private final WeekDayAssignmentRepository assignmentRepository;
    private final UserRepository userRepository;
    private final RoutineDayRepository routineDayRepository;

    public List<WeekTemplate> getAllByUser(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User", userId);
        }

        return weekTemplateRepository.findByUserId(userId);
    }

    public WeekTemplate create(WeekTemplate template, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        boolean exists = weekTemplateRepository
                .existsByUserIdAndName(userId, template.getName());

        if (exists) {
            throw new DuplicateResourceException(
                    "WeekTemplate already exists with name: " + template.getName()
            );
        }

        template.setUser(user);
        template.setIsDefault(false); // control total desde backend

        return weekTemplateRepository.save(template);
    }

    public WeekDayAssignment addDay(Long templateId, DayOfWeek day, Long routineDayId) {

        WeekTemplate template = findByIdOrThrow(templateId);

        // validate duplicate day
        if (assignmentRepository.existsByWeekTemplateIdAndDayOfWeek(templateId, day)) {
            throw new DayAlreadyAssignedException("Day already assigned: " + day);
        }

        RoutineDay routineDay = null;

        if (routineDayId != null) {
            routineDay = routineDayRepository.findById(routineDayId)
                    .orElseThrow(() -> new ResourceNotFoundException("RoutineDay", routineDayId));
        }

        WeekDayAssignment assignment = new WeekDayAssignment();
        assignment.setDayOfWeek(day);
        assignment.setWeekTemplate(template);
        assignment.setRoutineDay(routineDay);

        // maintain bidirectional relationship
        template.getDays().add(assignment);

        return assignmentRepository.save(assignment);
    }

    public void removeDay(Long assignmentId, Long userId) {

        WeekDayAssignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("WeekDayAssignment", assignmentId));

        WeekTemplate template = assignment.getWeekTemplate();

        // validate ownership
        if (!template.getUser().getId().equals(userId)) {
            throw new ForbiddenException("You do not have permission to modify this template");
        }

        template.getDays().remove(assignment); // orphanRemoval handles delete
    }

    public void setAsDefault(Long templateId, Long userId) {

        WeekTemplate template = findByIdOrThrow(templateId);

        if (!template.getUser().getId().equals(userId)) {
            throw new ForbiddenException("You do not have permission to modify this template");
        }

        // remove previous default if exists
        Optional<WeekTemplate> currentDefault =
                weekTemplateRepository.findByUserIdAndIsDefaultTrue(userId);

        currentDefault.ifPresent(t -> t.setIsDefault(false));

        template.setIsDefault(true);

        weekTemplateRepository.save(template);
    }

    public WeekTemplate findByIdOrThrow(Long templateId) {

        return weekTemplateRepository.findById(templateId)
                .orElseThrow(() -> new ResourceNotFoundException("WeekTemplate", templateId));
    }
}
