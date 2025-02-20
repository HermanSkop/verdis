package com.verdis.repositories;

import com.verdis.models.Discussion;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscussionRepository extends JpaRepository<Discussion, Long> {
    @EntityGraph(attributePaths = {"author"})
    Page<Discussion> findAll(@NonNull Pageable pageable);

    @EntityGraph(attributePaths = {"author", "comments", "comments.author"})
    Optional<Discussion> findById(@NonNull Long id);
}