package com.verdis.mappers;

import com.verdis.dtos.AccountDto;
import com.verdis.dtos.Role;
import com.verdis.models.account.Account;
import com.verdis.models.account.Admin;
import com.verdis.models.account.User;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AccountMapper {
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

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Account partialUpdate(AccountDto accountDto, @MappingTarget Account account);
}