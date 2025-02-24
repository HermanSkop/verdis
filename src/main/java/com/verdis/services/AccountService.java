package com.verdis.services;

import com.verdis.config.security.PasswordEncoder;
import com.verdis.dtos.LoginDto;
import com.verdis.dtos.RegisterDto;
import com.verdis.mappers.AdminMapper;
import com.verdis.mappers.UserMapper;
import com.verdis.models.account.Account;
import com.verdis.models.account.Admin;
import com.verdis.models.account.User;
import com.verdis.repositories.AccountRepository;
import com.verdis.repositories.AdminRepository;
import com.verdis.repositories.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;

@Component
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AdminMapper adminMapper;
    private final MailService mailService;
    private final AdminRepository adminRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, UserRepository userRepository, UserMapper userMapper, AdminMapper adminMapper, MailService mailService, AdminRepository adminRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.adminMapper = adminMapper;
        this.mailService = mailService;
        this.adminRepository = adminRepository;
    }

    @Transactional
    public void registerUser(@Valid RegisterDto registerDto) {
        validateRegisterDto(registerDto);

        User user = userMapper.toEntityFromRegisterDto(registerDto);
        String token = generateToken(16);
        user.setActivationToken(token);
        user.setPassword(PasswordEncoder.encryptPassword(registerDto.getPassword()));

        accountRepository.save(user);
        mailService.sendEmail(registerDto.getEmail(), "Activation", """
                Welcome to Verdis!
                Use the following link to activate your account:
                """ + generateActivationLink(token));
    }

    private void validateRegisterDto(RegisterDto registerDto) {
        if (!Objects.equals(registerDto.getPassword(), registerDto.getRepeatPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }
    }

    private String generateActivationLink(String token) {
        return "http://localhost:8080/activate?token=" + token;
    }

    private static String generateToken(int length) {
        byte[] bytes = new byte[length];
        new SecureRandom().nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    public void activate(String token) {
        User user = userRepository.findByActivationToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid token"));
        user.setActivationToken(null);
        accountRepository.save(user);
    }

    public Account authenticate(@Valid LoginDto loginDto) {
        Account account = accountRepository.findByUsername(loginDto.getUsernameOrEmail())
                .orElseGet(() -> accountRepository.findByEmail(loginDto.getUsernameOrEmail())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid username or email: " +
                                loginDto.getUsernameOrEmail())));
        if (!PasswordEncoder.matches(loginDto.getPassword(), account.getPassword()))
            throw new IllegalArgumentException("Invalid password");

        if (account instanceof User user) {
            if (user.getActivationToken() != null)
                throw new IllegalArgumentException("Account not activated");
        }
        return account;
    }

    public void invite(String email) {
        if (accountRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Account with email " + email + " already exists");
        }
        mailService.sendEmail(email, "Invitation",
                """
                        You have been invited to join Verdis as an administrator.
                        Use the following link to register:
                        """ + generateInvitation(email));
    }

    private String generateInvitation(String email) {
        String token = generateToken(16);
        adminRepository.save(Admin.builder().activationToken(token).email(email).build());
        return "http://localhost:8080/register?admin=" + token;
    }

    public void registerAdmin(@Valid RegisterDto registerDto, String token) {
        Admin admin = adminRepository.findByActivationToken(token).orElseThrow(() -> new IllegalArgumentException("Invalid invitation token"));
        validateRegisterDto(registerDto);
        if (!admin.getEmail().equals(registerDto.getEmail())) {
            throw new IllegalArgumentException("Email does not match the invited one: " + admin.getEmail());
        }

        admin.setUsername(registerDto.getUsername());
        admin.setPassword(PasswordEncoder.encryptPassword(registerDto.getPassword()));
        admin.setActivationToken(null);

        accountRepository.save(admin);
    }

    public Account getAccount(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }
}
