package com.matias.gymtracker.repository;

import com.matias.gymtracker.entity.RoutineDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoutineDayRepository extends JpaRepository<RoutineDay, Long> {
    List<RoutineDay> findByUserId(Long userId);

    boolean existsByUserIdAndNameAndMuscleGroup(Long userId, String name, String muscleGroup);

}