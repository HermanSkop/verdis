package com.verdis.services;

import com.verdis.dtos.RegisterDto;
import com.verdis.mappers.UserMapper;
import com.verdis.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserMapper userMapper;

    @Autowired
    public AccountService(AccountRepository accountRepository, UserMapper userMapper) {
        this.accountRepository = accountRepository;
        this.userMapper = userMapper;
    }

    public void register(RegisterDto registerDto) {
        validateRegisterDto(registerDto);
        accountRepository.save(userMapper.toEntityFromRegisterDto(registerDto));
    }

    private void validateRegisterDto(RegisterDto registerDto) {
        if (!Objects.equals(registerDto.getPassword(), registerDto.getRepeatPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }
    }
}
