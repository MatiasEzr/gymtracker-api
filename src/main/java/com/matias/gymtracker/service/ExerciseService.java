package com.matias.gymtracker.service;

import com.matias.gymtracker.entity.Exercise;
import com.matias.gymtracker.entity.RoutineDay;
import com.matias.gymtracker.exceptions.ForbiddenException;
import com.matias.gymtracker.exceptions.ResourceNotFoundException;
import com.matias.gymtracker.repository.ExerciseRepository;
import com.matias.gymtracker.repository.RoutineDayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final RoutineDayRepository routineDayRepository;

    /**
     * Get all exercises for a routine day
     */
    public List<Exercise> getAllByRoutineDay(Long dayId, Long userId) {

        validateRoutineDayOwnership(dayId, userId);

        return exerciseRepository.findByRoutineDayId(dayId);
    }

    /**
     * Add exercise to a routine day
     */
    public Exercise addToRoutineDay(Long dayId, Exercise exercise, Long userId) {

        RoutineDay routineDay = validateRoutineDayOwnership(dayId, userId);

        // maintain bidirectional consistency
        routineDay.getExercises().add(exercise);
        exercise.setRoutineDay(routineDay);

        return exerciseRepository.save(exercise);
    }

    /**
     * Update exercise fields
     */
    public Exercise update(Long exerciseId, Exercise updatedExercise, Long userId) {

        Exercise existing = findByIdOrThrow(exerciseId, userId);

        existing.setName(updatedExercise.getName());
        existing.setMuscle(updatedExercise.getMuscle());
        existing.setTargetSets(updatedExercise.getTargetSets());
        existing.setWeightKg(updatedExercise.getWeightKg());

        return exerciseRepository.save(existing);
    }

    /**
     * Delete exercise
     */
    public void delete(Long exerciseId, Long userId) {

        Exercise exercise = findByIdOrThrow(exerciseId, userId);
        exerciseRepository.delete(exercise);
    }

    /**
     * Find or throw
     */
    public Exercise findByIdOrThrow(Long exerciseId) {

        return exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise", exerciseId));
    }

    public Exercise findByIdOrThrow(Long exerciseId, Long userId) {
        Exercise exercise = findByIdOrThrow(exerciseId);

        if (exercise.getRoutineDay() == null || !exercise.getRoutineDay().getUser().getId().equals(userId)) {
            throw new ForbiddenException("You do not have access to this exercise");
        }

        return exercise;
    }

    private RoutineDay validateRoutineDayOwnership(Long dayId, Long userId) {
        RoutineDay routineDay = routineDayRepository.findById(dayId)
                .orElseThrow(() -> new ResourceNotFoundException("RoutineDay", dayId));

        if (!routineDay.getUser().getId().equals(userId)) {
            throw new ForbiddenException("You do not have access to this routine");
        }

        return routineDay;
    }
}
