package com.matias.gymtracker.mapper;

import com.matias.gymtracker.dto.request.WeekDayAssignmentCreateRequest;
import com.matias.gymtracker.dto.response.WeekDayAssignmentResponse;
import com.matias.gymtracker.entity.WeekDayAssignment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = CentralMapperConfig.class)
public interface WeekDayAssignmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "weekTemplate", ignore = true)
    @Mapping(source = "routineDayId", target = "routineDay.id")
    WeekDayAssignment toEntity(WeekDayAssignmentCreateRequest dto);

    @Mapping(source = "weekTemplate.id", target = "weekTemplateId")
    @Mapping(source = "routineDay.id", target = "routineDayId")
    WeekDayAssignmentResponse toResponse(WeekDayAssignment entity);

    List<WeekDayAssignmentResponse> toResponseList(List<WeekDayAssignment> entities);
}
