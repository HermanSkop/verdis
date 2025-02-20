package com.verdis.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.verdis.models.Discussion}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDiscussionDto {
    private AccountDto author;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
