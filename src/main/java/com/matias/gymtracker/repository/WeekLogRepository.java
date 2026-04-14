package com.matias.gymtracker.repository;

import com.matias.gymtracker.entity.WeekLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WeekLogRepository extends JpaRepository<WeekLog, Long> {

    List<WeekLog> findByUserId(Long userId);

    Optional<WeekLog> findByUserIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Long userId,
            LocalDate date
    );

    @Query("SELECT w FROM WeekLog w LEFT JOIN FETCH w.sessions WHERE w.id = :id")
    Optional<WeekLog> findByIdWithSessions(Long id);
}