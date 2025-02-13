package com.verdis.mappers;

import com.verdis.dtos.RegisterDto;
import com.verdis.models.account.User;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "comments", expression = "java(new java.util.ArrayList<>())")
    User toEntityFromRegisterDto(RegisterDto registerDto);

    RegisterDto toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(RegisterDto registerDto, @MappingTarget User user);
}