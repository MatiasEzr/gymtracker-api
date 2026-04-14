package com.matias.gymtracker.service;

import com.matias.gymtracker.dto.request.SetLogCreateRequest;
import com.matias.gymtracker.dto.request.SetLogUpdateRequest;
import com.matias.gymtracker.entity.ExerciseLog;
import com.matias.gymtracker.entity.SetLog;
import com.matias.gymtracker.exceptions.ResourceNotFoundException;
import com.matias.gymtracker.mapper.SetLogMapper;
import com.matias.gymtracker.repository.SetLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SetLogService {

    private final SetLogRepository setLogRepository;
    private final ExerciseLogService exerciseLogService;
    private final SetLogMapper setLogMapper;

    @Transactional
    public SetLog addToExerciseLog(Long exerciseLogId, SetLogCreateRequest dto, Long userId) {
        ExerciseLog exerciseLog = exerciseLogService.findById(exerciseLogId, userId);
        SetLog setLog = setLogMapper.toEntity(dto);

        setLog.setExerciseLog(exerciseLog);
        exerciseLog.getSets().add(setLog);

        return setLogRepository.save(setLog);
    }

    @Transactional
    public SetLog update(Long setId, SetLogUpdateRequest dto, Long userId) {
        SetLog existing = findById(setId, userId);
        setLogMapper.updateEntityFromDto(dto, existing);

        return setLogRepository.save(existing);
    }

    @Transactional
    public void delete(Long setId, Long userId) {
        SetLog setLog = findById(setId, userId);
        setLogRepository.delete(setLog);
    }

    @Transactional(readOnly = true)
    public SetLog findById(Long setId, Long userId) {
        SetLog setLog = setLogRepository.findById(setId)
                .orElseThrow(() -> new ResourceNotFoundException("SetLog", setId));

        exerciseLogService.findById(setLog.getExerciseLog().getId(), userId);

        return setLog;
    }
}
