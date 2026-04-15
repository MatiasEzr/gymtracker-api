package com.matias.gymtracker.mapper;

import com.matias.gymtracker.dto.request.UserCreateRequest;
import com.matias.gymtracker.dto.response.UserResponse;
import com.matias.gymtracker.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = CentralMapperConfig.class)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    User toEntity(UserCreateRequest dto);

    UserResponse toResponse(User user);

    List<UserResponse> toResponseList(List<User> users);
}
