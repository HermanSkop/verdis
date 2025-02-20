package com.verdis.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.verdis.models.Discussion}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscussionPreviewDto implements Serializable {
    private Long id;
    private LocalDateTime updatedDateTime;
    private AccountDto author;
    @NotBlank
    private String title;
    private boolean archived;
}