package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.NonFinal;
import ru.practicum.shareit.booking.dto.BookingDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ItemDto {
    Long id;
    @NotNull
    @NotBlank
    String name;
    @NotNull
    @NotBlank
    String description;
    @NotNull
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
}
