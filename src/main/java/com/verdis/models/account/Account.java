package com.verdis.models.account;

import com.verdis.config.AppConfig;
import com.verdis.models.Comment;
import com.verdis.models.Discussion;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.proxy.HibernateProxy;

import java.util.List;
import java.util.Objects;

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
    private String username;

    private String password;

    @Pattern(regexp = AppConfig.EMAIL_PATTERN, message = AppConfig.EMAIL_PATTERN_MESSAGE)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(unique = true)
    private String activationToken;

    @OneToMany(mappedBy = "archivedBy")
    private List<Discussion> archivedDiscussions = List.of();

    @OneToMany(mappedBy = "author")
    private List<Discussion> authoredDiscussions = List.of();

    @OneToMany(mappedBy = "author")
    private List<Comment> comments = List.of();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Account account = (Account) o;
        return getId() != null && Objects.equals(getId(), account.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}