package ru.practicum.shareit.item.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import ru.practicum.shareit.booking.dto.BookingDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Value
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemDto {
    @NonFinal
    Long id;
    @NotBlank
    @NonFinal
    String name;
    @NotBlank
    @NonFinal
    String description;
    @NotNull
    @NonFinal
    Boolean available;
    @NonFinal
    @Setter
    BookingDto lastBooking;
    @NonFinal
    @Setter
    BookingDto nextBooking;
    @NonFinal
    @Setter
    List<CommentDto> comments;
    @NonFinal
    @Setter
    Long requestId;
}
