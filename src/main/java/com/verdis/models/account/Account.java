package com.verdis.models.account;

import com.verdis.config.AppConfig;
import com.verdis.models.Discussion;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = AppConfig.USERNAME_PATTERN, message = AppConfig.USERNAME_PATTERN_MESSAGE)
    @Column(name = "username", nullable = false)
    private String username;

    @Pattern(regexp = AppConfig.PASSWORD_PATTERN, message = AppConfig.PASSWORD_PATTERN_MESSAGE)
    @Column(name = "password", nullable = false)
    private String password;

    @Pattern(regexp = AppConfig.EMAIL_PATTERN, message = AppConfig.EMAIL_PATTERN_MESSAGE)
    @Column(name = "email", nullable = false)
    private String email;

    @OneToMany(mappedBy = "archivedBy")
    private List<Discussion> archivedDiscussions;

    @OneToMany(mappedBy = "author")
    private List<Discussion> authoredDiscussions;
}