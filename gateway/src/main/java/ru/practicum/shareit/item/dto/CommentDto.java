package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Builder
@Value
public class CommentDto {
    Long id;
    @NotBlank
    String text;
    LocalDateTime created;
    String authorName;
}
