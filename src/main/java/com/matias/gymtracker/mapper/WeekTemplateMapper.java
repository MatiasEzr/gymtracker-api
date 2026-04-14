package com.matias.gymtracker.mapper;

import com.matias.gymtracker.dto.request.WeekTemplateCreateRequest;
import com.matias.gymtracker.dto.request.WeekTemplateUpdateRequest;
import com.matias.gymtracker.dto.response.WeekTemplateResponse;
import com.matias.gymtracker.entity.WeekDayAssignment;
import com.matias.gymtracker.entity.WeekTemplate;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(config = CentralMapperConfig.class)
public interface WeekTemplateMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "days", ignore = true)
    @Mapping(target = "isDefault", ignore = true)
    WeekTemplate toEntity(WeekTemplateCreateRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "days", ignore = true)
    void updateEntityFromDto(WeekTemplateUpdateRequest dto, @MappingTarget WeekTemplate entity);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "days", target = "dayIds")
    WeekTemplateResponse toResponse(WeekTemplate entity);

    List<WeekTemplateResponse> toResponseList(List<WeekTemplate> entities);

    default List<Long> mapDaysToIds(List<WeekDayAssignment> days) {
        return days == null ? List.of() : days.stream().map(WeekDayAssignment::getId).toList();
    }
}
