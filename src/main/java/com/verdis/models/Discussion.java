package com.verdis.models;

import com.verdis.models.account.Account;
import com.verdis.models.account.Admin;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Discussion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @UpdateTimestamp
    private LocalDateTime updatedDateTime;

    @ManyToOne
    private Account archivedBy;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Account author;

    @OneToMany(mappedBy = "discussion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = List.of();

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    public boolean isArchived() {
        return archivedBy != null;
    }

}