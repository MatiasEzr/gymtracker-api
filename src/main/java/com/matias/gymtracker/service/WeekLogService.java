package com.matias.gymtracker.service;

import com.matias.gymtracker.entity.User;
import com.matias.gymtracker.entity.WeekLog;
import com.matias.gymtracker.exceptions.ResourceNotFoundException;
import com.matias.gymtracker.repository.UserRepository;
import com.matias.gymtracker.repository.WeekLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeekLogService {

    private final WeekLogRepository weekLogRepository;
    private final UserRepository userRepository;

    public List<WeekLog> getAllByUser(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User", userId);
        }

        return weekLogRepository.findByUserId(userId);
    }

    public WeekLog getById(Long weekId) {

        return weekLogRepository.findByIdWithSessions(weekId)
                .orElseThrow(() -> new ResourceNotFoundException("WeekLog", weekId));
    }

    public WeekLog findOrCreateCurrentWeek(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        ZoneId zone = ZoneId.of("America/Argentina/Buenos_Aires");
        LocalDate today = LocalDate.now(zone);

        // calculate monday and sunday
        LocalDate start = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate end = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        // week number and year
        WeekFields weekFields = WeekFields.ISO;
        int weekNumber = today.get(weekFields.weekOfWeekBasedYear());
        int year = today.getYear();

        // find existing
        Optional<WeekLog> existing =
                weekLogRepository.findByUserIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        userId,
                        today
                );

        if (existing.isPresent()) {
            return existing.get();
        }

        // create new
        WeekLog newWeek = new WeekLog(start, end, weekNumber, year, user);

        return weekLogRepository.save(newWeek);
    }
}