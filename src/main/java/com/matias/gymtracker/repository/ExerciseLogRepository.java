package com.matias.gymtracker.repository;

import com.matias.gymtracker.entity.ExerciseLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseLogRepository extends CrudRepository<ExerciseLog, Long> {
}
