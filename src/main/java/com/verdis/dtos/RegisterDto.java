package com.verdis.dtos;

import com.verdis.config.AppConfig;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.verdis.models.account.Account}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto implements Serializable {
    @Pattern(message = AppConfig.USERNAME_PATTERN_MESSAGE, regexp = AppConfig.USERNAME_PATTERN)
    private String username;
    @Pattern(message = AppConfig.PASSWORD_PATTERN_MESSAGE, regexp = AppConfig.PASSWORD_PATTERN)
    private String password;
    @Pattern(message = AppConfig.PASSWORD_PATTERN_MESSAGE, regexp = AppConfig.PASSWORD_PATTERN)
    private String repeatPassword;
    @Pattern(message = AppConfig.EMAIL_PATTERN_MESSAGE, regexp = AppConfig.EMAIL_PATTERN)
    private String email;
}