package com.matias.gymtracker.service;

import com.matias.gymtracker.entity.Exercise;
import com.matias.gymtracker.entity.RoutineDay;
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
    public List<Exercise> getAllByRoutineDay(Long dayId) {

        if (!routineDayRepository.existsById(dayId)) {
            throw new ResourceNotFoundException("RoutineDay", dayId);
        }

        return exerciseRepository.findByRoutineDayId(dayId);
    }

    /**
     * Add exercise to a routine day
     */
    public Exercise addToRoutineDay(Long dayId, Exercise exercise) {

        RoutineDay routineDay = routineDayRepository.findById(dayId)
                .orElseThrow(() -> new ResourceNotFoundException("RoutineDay", dayId));

        // maintain bidirectional consistency
        routineDay.getExercises().add(exercise);
        exercise.setRoutineDay(routineDay);

        return exerciseRepository.save(exercise);
    }

    /**
     * Update exercise fields
     */
    public Exercise update(Long exerciseId, Exercise updatedExercise) {

        Exercise existing = findByIdOrThrow(exerciseId);

        existing.setName(updatedExercise.getName());
        existing.setMuscle(updatedExercise.getMuscle());
        existing.setTargetSets(updatedExercise.getTargetSets());
        existing.setWeightKg(updatedExercise.getWeightKg());

        return exerciseRepository.save(existing);
    }

    /**
     * Delete exercise
     */
    public void delete(Long exerciseId) {

        Exercise exercise = findByIdOrThrow(exerciseId);
        exerciseRepository.delete(exercise);
    }

    /**
     * Find or throw
     */
    public Exercise findByIdOrThrow(Long exerciseId) {

        return exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise", exerciseId));
    }
}