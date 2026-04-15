package com.matias.gymtracker.mapper;

import com.matias.gymtracker.dto.request.WeekTemplateCreateRequest;
import com.matias.gymtracker.dto.response.WeekTemplateResponse;
import com.matias.gymtracker.entity.WeekDayAssignment;
import com.matias.gymtracker.entity.WeekTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = CentralMapperConfig.class)
public interface WeekTemplateMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "days", ignore = true)
    @Mapping(target = "isDefault", ignore = true)
    WeekTemplate toEntity(WeekTemplateCreateRequest dto);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "days", target = "dayIds")
    WeekTemplateResponse toResponse(WeekTemplate entity);

    List<WeekTemplateResponse> toResponseList(List<WeekTemplate> entities);

    default List<Long> mapDaysToIds(List<WeekDayAssignment> days) {
        return days == null ? List.of() : days.stream().map(WeekDayAssignment::getId).toList();
    }
}
