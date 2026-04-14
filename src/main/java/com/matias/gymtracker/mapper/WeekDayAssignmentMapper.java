package com.matias.gymtracker.mapper;

import com.matias.gymtracker.dto.request.WeekDayAssignmentCreateRequest;
import com.matias.gymtracker.dto.request.WeekDayAssignmentUpdateRequest;
import com.matias.gymtracker.dto.response.WeekDayAssignmentResponse;
import com.matias.gymtracker.entity.WeekDayAssignment;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(config = CentralMapperConfig.class)
public interface WeekDayAssignmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "weekTemplate", ignore = true)
    @Mapping(source = "routineDayId", target = "routineDay.id")
    WeekDayAssignment toEntity(WeekDayAssignmentCreateRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "weekTemplate", ignore = true)
    @Mapping(source = "routineDayId", target = "routineDay.id")
    void updateEntityFromDto(WeekDayAssignmentUpdateRequest dto, @MappingTarget WeekDayAssignment entity);

    @Mapping(source = "weekTemplate.id", target = "weekTemplateId")
    @Mapping(source = "routineDay.id", target = "routineDayId")
    WeekDayAssignmentResponse toResponse(WeekDayAssignment entity);

    List<WeekDayAssignmentResponse> toResponseList(List<WeekDayAssignment> entities);
}
