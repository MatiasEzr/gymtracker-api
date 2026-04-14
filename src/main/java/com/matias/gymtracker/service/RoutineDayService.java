package com.matias.gymtracker.service;

import com.matias.gymtracker.entity.RoutineDay;
import com.matias.gymtracker.entity.User;
import com.matias.gymtracker.exceptions.DuplicateResourceException;
import com.matias.gymtracker.exceptions.ForbiddenException;
import com.matias.gymtracker.exceptions.ResourceNotFoundException;
import com.matias.gymtracker.repository.RoutineDayRepository;
import com.matias.gymtracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoutineDayService {

    private final RoutineDayRepository routineDayRepository;
    private final UserRepository userRepository;

    public RoutineDay create(RoutineDay routineDay, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        // validate duplicate
        boolean exists = routineDayRepository.existsByUserIdAndNameAndMuscleGroup(
                userId,
                routineDay.getName(),
                routineDay.getMuscleGroup()
        );

        if (exists) {
            throw new DuplicateResourceException("Routine already exists for this user");
        }

        routineDay.setUser(user);

        return routineDayRepository.save(routineDay);
    }

    public RoutineDay update(Long routineDayId, RoutineDay updated, Long userId) {

        RoutineDay existing = findByIdOrThrow(routineDayId);

        // validate ownership
        if (!existing.getUser().getId().equals(userId)) {
            throw new ForbiddenException("You do not have permission to modify this routine");
        }

        // controlled update (partial-safe)
        if (updated.getName() != null) {
            existing.setName(updated.getName());
        }

        if (updated.getMuscleGroup() != null) {
            existing.setMuscleGroup(updated.getMuscleGroup());
        }

        return routineDayRepository.save(existing);
    }

    public void delete(Long routineDayId, Long userId) {

        RoutineDay existing = findByIdOrThrow(routineDayId);

        // validate ownership
        if (!existing.getUser().getId().equals(userId)) {
            throw new ForbiddenException("You do not have permission to delete this routine");
        }

        // cascade handles exercises automatically
        routineDayRepository.delete(existing);
    }

    public RoutineDay findByIdOrThrow(Long id) {
        return routineDayRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RoutineDay", id));
    }


    public List<RoutineDay> getAllByUser(Long userId) {

        // optional: validar que el usuario existe (recomendado)
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User", userId);
        }

        return routineDayRepository.findByUserId(userId);
    }
}