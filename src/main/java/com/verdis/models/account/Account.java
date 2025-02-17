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

    @Column(name = "password", nullable = false)
    private String password;

    @Pattern(regexp = AppConfig.EMAIL_PATTERN, message = AppConfig.EMAIL_PATTERN_MESSAGE)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "archivedBy")
    private List<Discussion> archivedDiscussions = List.of();

    @OneToMany(mappedBy = "author")
    private List<Discussion> authoredDiscussions = List.of();
}