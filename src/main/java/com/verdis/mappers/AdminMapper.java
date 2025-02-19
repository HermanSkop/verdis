package com.verdis.mappers;

import com.verdis.dtos.RegisterDto;
import com.verdis.models.account.Admin;
import com.verdis.models.account.User;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface AdminMapper {
    Admin toEntityFromRegisterDto(RegisterDto registerDto);

    Admin toEntityFromLoginDto(RegisterDto registerDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Admin partialUpdate(RegisterDto registerDto, @MappingTarget Admin user);
}