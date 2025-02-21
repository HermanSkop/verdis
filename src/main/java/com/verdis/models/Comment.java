package com.verdis.models;

import com.verdis.models.account.Account;
import com.verdis.models.account.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String content;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Account author;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Discussion discussion;

}