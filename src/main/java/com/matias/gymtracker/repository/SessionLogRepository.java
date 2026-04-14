package com.matias.gymtracker.repository;

import com.matias.gymtracker.entity.SessionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface SessionLogRepository extends JpaRepository<SessionLog, Long> {

    boolean existsByWeekLogIdAndDate(Long weekLogId, LocalDate date);

    @Query("SELECT s FROM SessionLog s LEFT JOIN FETCH s.exerciseLogs WHERE s.id = :id")
    Optional<SessionLog> findByIdWithExercises(Long id);
}