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
public class LoginDto implements Serializable {
    private String usernameOrEmail;
    @Pattern(regexp = AppConfig.PASSWORD_PATTERN, message = AppConfig.PASSWORD_PATTERN_MESSAGE)
    private String password;
}