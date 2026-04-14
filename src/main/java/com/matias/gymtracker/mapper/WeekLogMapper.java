package com.matias.gymtracker.mapper;

import com.matias.gymtracker.dto.request.WeekLogCreateRequest;
import com.matias.gymtracker.dto.request.WeekLogUpdateRequest;
import com.matias.gymtracker.dto.response.WeekLogResponse;
import com.matias.gymtracker.entity.SessionLog;
import com.matias.gymtracker.entity.WeekLog;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(config = CentralMapperConfig.class)
public interface WeekLogMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "sessions", ignore = true)
    WeekLog toEntity(WeekLogCreateRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "sessions", ignore = true)
    void updateEntityFromDto(WeekLogUpdateRequest dto, @MappingTarget WeekLog entity);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "sessions", target = "sessionIds")
    WeekLogResponse toResponse(WeekLog entity);

    List<WeekLogResponse> toResponseList(List<WeekLog> entities);

    default List<Long> mapSessionsToIds(List<SessionLog> sessions) {
        return sessions == null ? List.of() : sessions.stream().map(SessionLog::getId).toList();
    }
}
