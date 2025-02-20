package com.verdis.mappers;

import com.verdis.dtos.AccountDto;
import com.verdis.dtos.Role;
import com.verdis.models.account.Account;
import com.verdis.models.account.Admin;
import com.verdis.models.account.User;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AccountMapper {
    @Named("mapAccount")
    @Mapping(target = "role", expression = "java(mapRole(account))")
    AccountDto toDto(Account account);

    default Role mapRole(Account account) {
        if (account instanceof Admin) {
            return Role.ROLE_ADMIN;
        } else if (account instanceof User) {
            return Role.ROLE_USER;
        }
        return null;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "authToken", ignore = true)
    @Mapping(target = "comments", ignore = true)
    default Account toEntity(AccountDto dto) {
        if (dto == null || dto.getRole() == null) {
            throw new IllegalArgumentException("Role is required to create an account entity");
        }

        return switch (dto.getRole()) {
            case ROLE_ADMIN -> Admin.builder().email(dto.getEmail()).username(dto.getUsername()).build();
            case ROLE_USER -> User.builder().email(dto.getEmail()).username(dto.getUsername()).build();
        };
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Account partialUpdate(AccountDto accountDto, @MappingTarget Account account);
}