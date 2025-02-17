package com.verdis.models.account;

import com.verdis.models.Comment;
import com.verdis.models.Discussion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class User extends Account {
    @OneToMany(mappedBy = "user")
    private List<Comment> comments = List.of();

    @Column(unique = true)
    private String activationToken;

    @PrePersist
    @PreUpdate
    private void validate() {
        if (comments.stream().map(Comment::getUser).anyMatch(user -> user != this)) {
            throw new IllegalArgumentException("Comment's user must be the same as the user");
        }
    }
}