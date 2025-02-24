package com.verdis.controllers;

import com.verdis.dtos.AccountDto;
import com.verdis.dtos.LoginDto;
import com.verdis.dtos.RegisterDto;
import com.verdis.mappers.AccountMapper;
import com.verdis.services.AccountService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Controller
public class AccountController {
    private final AccountService accountService;
    private final AccountMapper accountMapper;

    public AccountController(AccountService accountService, AccountMapper accountMapper) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginDto") LoginDto loginDto, HttpSession session) {
        AccountDto accountDto = accountMapper.toDto(accountService.authenticate(loginDto));

        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(accountDto.getRole().name()));
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(accountDto, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
        session.setAttribute("user", accountDto);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("registerDto", new RegisterDto());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerDto") RegisterDto registerDto, @RequestParam("admin") String token) {
        if (token != null && !token.isEmpty()) {
            accountService.registerAdmin(registerDto, token);
        } else {
            accountService.registerUser(registerDto);
        }
        return "redirect:/login";
    }

    @GetMapping("/activate")
    public String activate(@RequestParam("token") String token) {
        accountService.activate(token);
        return "redirect:/login";
    }

    @GetMapping("/account")
    public String account(Model model, HttpSession session) {
        model.addAttribute("accountDto", session.getAttribute("user"));
        return "account";
    }

    @PostMapping("/invite")
    public ResponseEntity<Void> invite(@RequestParam("email") String email) {
        accountService.invite(email);
        return ResponseEntity.ok().build();
    }

}
