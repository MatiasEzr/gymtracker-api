package com.matias.gymtracker.mapper;

import com.matias.gymtracker.dto.request.ExerciseLogCreateRequest;
import com.matias.gymtracker.dto.request.ExerciseLogUpdateRequest;
import com.matias.gymtracker.dto.response.ExerciseLogResponse;
import com.matias.gymtracker.entity.ExerciseLog;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(config = CentralMapperConfig.class, uses = SetLogMapper.class)
public interface ExerciseLogMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "exerciseId", target = "exercise.id")
    @Mapping(target = "sessionLog", ignore = true)
    ExerciseLog toEntity(ExerciseLogCreateRequest dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "exercise", ignore = true)
    @Mapping(target = "sessionLog", ignore = true)
    ExerciseLog toEntity(ExerciseLogUpdateRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "exercise", ignore = true)
    @Mapping(target = "sessionLog", ignore = true)
    void updateEntityFromDto(ExerciseLogUpdateRequest dto, @MappingTarget ExerciseLog entity);

    @Mapping(source = "sessionLog.id", target = "sessionId")
    @Mapping(source = "exercise.id", target = "exerciseId")
    @Mapping(source = "exercise.name", target = "exerciseName")
    ExerciseLogResponse toResponse(ExerciseLog entity);

    List<ExerciseLogResponse> toResponseList(List<ExerciseLog> entities);
}
