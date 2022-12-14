package ru.practicum.shareit.booking.dto;

import lombok.Builder;
import lombok.Value;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.Past;
import java.time.LocalDateTime;

@Builder
@Value
public class BookingDto {
    Long id;
    LocalDateTime start;
    LocalDateTime end;
    Status status;
    Long itemId;
    Long bookerId;
    User booker;
    Item item;
}
