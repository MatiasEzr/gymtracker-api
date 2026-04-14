package com.matias.gymtracker.mapper;

import com.matias.gymtracker.dto.request.ExerciseCreateRequest;
import com.matias.gymtracker.dto.request.ExerciseUpdateRequest;
import com.matias.gymtracker.dto.response.ExerciseResponse;
import com.matias.gymtracker.entity.Exercise;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(config = CentralMapperConfig.class)
public interface ExerciseMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "routineDay", ignore = true)
    Exercise toEntity(ExerciseCreateRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "routineDay", ignore = true)
    void updateEntityFromDto(ExerciseUpdateRequest dto, @MappingTarget Exercise entity);

    @Mapping(source = "routineDay.id", target = "routineDayId")
    ExerciseResponse toResponse(Exercise entity);

    List<ExerciseResponse> toResponseList(List<Exercise> entities);
}
