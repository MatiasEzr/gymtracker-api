package com.matias.gymtracker.repository;

import com.matias.gymtracker.entity.WeekDayAssignment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.Optional;

@Repository
public interface WeekDayAssignmentRepository extends CrudRepository<WeekDayAssignment, Long> {
    Optional<WeekDayAssignment> findById(Long id);

    boolean existsByWeekTemplateIdAndDayOfWeek(Long templateId, DayOfWeek dayOfWeek);

}
