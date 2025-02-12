package com.verdis.models;

import com.verdis.models.account.Account;
import com.verdis.models.account.Admin;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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

    @ManyToOne
    private Account archivedBy;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Account author;

    @OneToMany(mappedBy = "discussion")
    private List<Comment> comments;

    public boolean isArchived() {
        return archivedBy != null;
    }

}