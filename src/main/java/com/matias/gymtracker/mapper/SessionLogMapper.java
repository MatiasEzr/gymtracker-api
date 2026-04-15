package com.matias.gymtracker.mapper;

import com.matias.gymtracker.dto.request.SessionLogCreateRequest;
import com.matias.gymtracker.dto.response.SessionLogResponse;
import com.matias.gymtracker.entity.ExerciseLog;
import com.matias.gymtracker.entity.SessionLog;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = CentralMapperConfig.class)
public interface SessionLogMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", ignore = true)
    @Mapping(target = "completed", ignore = true)
    @Mapping(target = "weekLog", ignore = true)
    @Mapping(target = "routineDay.id", source = "routineDayId")
    @Mapping(target = "exerciseLogs", ignore = true)
    SessionLog toEntity(SessionLogCreateRequest dto);

    @Mapping(source = "weekLog.id", target = "weekLogId")
    @Mapping(source = "routineDay.id", target = "routineDayId")
    @Mapping(source = "exerciseLogs", target = "exerciseLogIds")
    SessionLogResponse toResponse(SessionLog entity);

    List<SessionLogResponse> toResponseList(List<SessionLog> entities);

    default List<Long> mapExerciseLogsToIds(List<ExerciseLog> exerciseLogs) {
        return exerciseLogs == null ? List.of() : exerciseLogs.stream().map(ExerciseLog::getId).toList();
    }
}
