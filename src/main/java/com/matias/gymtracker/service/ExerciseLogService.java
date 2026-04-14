package com.matias.gymtracker.service;

import com.matias.gymtracker.entity.Exercise;
import com.matias.gymtracker.entity.ExerciseLog;
import com.matias.gymtracker.entity.SessionLog;
import com.matias.gymtracker.entity.SetLog;
import com.matias.gymtracker.exceptions.ForbiddenException;
import com.matias.gymtracker.exceptions.ResourceNotFoundException;
import com.matias.gymtracker.repository.ExerciseLogRepository;
import com.matias.gymtracker.repository.ExerciseRepository;
import com.matias.gymtracker.repository.SessionLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExerciseLogService {

    private final ExerciseLogRepository exerciseLogRepository;
    private final SessionLogRepository sessionLogRepository;
    private final ExerciseRepository exerciseRepository;

    @Transactional
    public ExerciseLog addToSession(Long sessionId, ExerciseLog exerciseLog, Long userId) {

        SessionLog session = sessionLogRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("SessionLog", sessionId));

        validateSessionOwnership(session, userId);

        Long exerciseId = exerciseLog.getExercise() != null ? exerciseLog.getExercise().getId() : null;
        if (exerciseId == null) {
            throw new ResourceNotFoundException("Exercise", null);
        }

        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise", exerciseId));

        validateExerciseBelongsToSessionRoutine(session, exercise);

        exerciseLog.setSessionLog(session);
        exerciseLog.setExercise(exercise);

        if (exerciseLog.getSets() != null) {
            for (SetLog setLog : exerciseLog.getSets()) {
                setLog.setExerciseLog(exerciseLog);
            }
        }

        session.getExerciseLogs().add(exerciseLog);

        return exerciseLogRepository.save(exerciseLog);
    }

    @Transactional
    public ExerciseLog update(Long exerciseLogId, ExerciseLog updatedExerciseLog, Long userId) {

        ExerciseLog existing = findById(exerciseLogId, userId);

        if (updatedExerciseLog.getWeightUsed() != null) {
            existing.setWeightUsed(updatedExerciseLog.getWeightUsed());
        }

        if (updatedExerciseLog.getSetsCompleted() != null) {
            existing.setSetsCompleted(updatedExerciseLog.getSetsCompleted());
        }

        if (updatedExerciseLog.getCompletedMinimum() != null) {
            existing.setCompletedMinimum(updatedExerciseLog.getCompletedMinimum());
        }

        if (updatedExerciseLog.getCompletedFull() != null) {
            existing.setCompletedFull(updatedExerciseLog.getCompletedFull());
        }

        if (updatedExerciseLog.getSets() != null) {
            existing.getSets().clear();

            for (SetLog setLog : updatedExerciseLog.getSets()) {
                setLog.setId(null);
                setLog.setExerciseLog(existing);
                existing.getSets().add(setLog);
            }
        }

        return exerciseLogRepository.save(existing);
    }

    @Transactional
    public void delete(Long exerciseLogId, Long userId) {

        ExerciseLog exerciseLog = findById(exerciseLogId, userId);
        exerciseLogRepository.delete(exerciseLog);
    }

    @Transactional(readOnly = true)
    public ExerciseLog findById(Long exerciseLogId, Long userId) {

        ExerciseLog exerciseLog = exerciseLogRepository.findById(exerciseLogId)
                .orElseThrow(() -> new ResourceNotFoundException("ExerciseLog", exerciseLogId));

        validateSessionOwnership(exerciseLog.getSessionLog(), userId);

        return exerciseLog;
    }

    private void validateSessionOwnership(SessionLog session, Long userId) {
        if (!session.getWeekLog().getUser().getId().equals(userId)) {
            throw new ForbiddenException("You do not have access to this session");
        }
    }

    private void validateExerciseBelongsToSessionRoutine(SessionLog session, Exercise exercise) {
        if (exercise.getRoutineDay() == null || !exercise.getRoutineDay().getId().equals(session.getRoutineDay().getId())) {
            throw new ForbiddenException("The exercise does not belong to this session routine");
        }
    }
}
