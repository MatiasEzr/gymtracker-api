package com.matias.gymtracker.mapper;

import com.matias.gymtracker.dto.request.UserCreateRequest;
import com.matias.gymtracker.dto.request.UserUpdateRequest;
import com.matias.gymtracker.dto.response.UserResponse;
import com.matias.gymtracker.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(config = CentralMapperConfig.class)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    User toEntity(UserCreateRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromDto(UserUpdateRequest dto, @MappingTarget User entity);

    UserResponse toResponse(User user);

    List<UserResponse> toResponseList(List<User> users);
}
