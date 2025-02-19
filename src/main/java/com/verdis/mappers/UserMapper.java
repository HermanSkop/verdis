package com.verdis.mappers;

import com.verdis.dtos.RegisterDto;
import com.verdis.models.account.User;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface UserMapper {
    @Mapping(target = "comments", expression = "java(new java.util.ArrayList<>())")
    User toEntityFromRegisterDto(RegisterDto registerDto);

    @Mapping(target = "comments", expression = "java(new java.util.ArrayList<>())")
    User toEntityFromLoginDto(RegisterDto registerDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(RegisterDto registerDto, @MappingTarget User user);
}