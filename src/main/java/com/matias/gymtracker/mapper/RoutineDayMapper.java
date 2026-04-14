package com.matias.gymtracker.mapper;

import com.matias.gymtracker.dto.request.RoutineDayCreateRequest;
import com.matias.gymtracker.dto.request.RoutineDayUpdateRequest;
import com.matias.gymtracker.dto.response.RoutineDayResponse;
import com.matias.gymtracker.entity.Exercise;
import com.matias.gymtracker.entity.RoutineDay;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(config = CentralMapperConfig.class)
public interface RoutineDayMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "exercises", ignore = true)
    RoutineDay toEntity(RoutineDayCreateRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "exercises", ignore = true)
    void updateEntityFromDto(RoutineDayUpdateRequest dto, @MappingTarget RoutineDay entity);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "exercises", target = "exerciseIds")
    RoutineDayResponse toResponse(RoutineDay entity);

    List<RoutineDayResponse> toResponseList(List<RoutineDay> entities);

    default List<Long> mapExercisesToIds(List<Exercise> exercises) {
        return exercises == null ? List.of() : exercises.stream().map(Exercise::getId).toList();
    }
}
