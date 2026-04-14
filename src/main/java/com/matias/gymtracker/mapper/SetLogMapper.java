package com.matias.gymtracker.mapper;

import com.matias.gymtracker.dto.request.SetLogCreateRequest;
import com.matias.gymtracker.dto.request.SetLogUpdateRequest;
import com.matias.gymtracker.dto.response.SetLogResponse;
import com.matias.gymtracker.dto.response.SetLogSummaryResponse;
import com.matias.gymtracker.entity.SetLog;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(config = CentralMapperConfig.class)
public interface SetLogMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "exerciseLog", ignore = true)
    SetLog toEntity(SetLogCreateRequest dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "exerciseLog", ignore = true)
    SetLog toEntity(SetLogUpdateRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "exerciseLog", ignore = true)
    void updateEntityFromDto(SetLogUpdateRequest dto, @MappingTarget SetLog entity);

    SetLogResponse toResponse(SetLog entity);

    @Mapping(source = "exerciseLog.id", target = "exerciseLogId")
    SetLogSummaryResponse toSummaryResponse(SetLog entity);

    List<SetLogResponse> toResponseList(List<SetLog> entities);
}
