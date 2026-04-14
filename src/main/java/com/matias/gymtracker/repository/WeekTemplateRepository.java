package com.matias.gymtracker.repository;

import com.matias.gymtracker.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByUser_Id(Long id);
}
