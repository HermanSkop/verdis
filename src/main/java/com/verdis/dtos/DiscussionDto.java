package com.verdis.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link com.verdis.models.Discussion}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscussionDto implements Serializable {
    private Long id;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private AccountDto archivedBy;
    private AccountDto author;
    private List<CommentDto> comments = List.of();
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}