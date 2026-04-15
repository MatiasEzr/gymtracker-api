package com.matias.gymtracker.mapper;

import com.matias.gymtracker.dto.response.WeekLogResponse;
import com.matias.gymtracker.entity.SessionLog;
import com.matias.gymtracker.entity.WeekLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = CentralMapperConfig.class)
public interface WeekLogMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "sessions", target = "sessionIds")
    WeekLogResponse toResponse(WeekLog entity);

    List<WeekLogResponse> toResponseList(List<WeekLog> entities);

    default List<Long> mapSessionsToIds(List<SessionLog> sessions) {
        return sessions == null ? List.of() : sessions.stream().map(SessionLog::getId).toList();
    }
}
