package ru.practicum.shareit.user.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Value
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    @NonFinal
    Long id;
    @NonFinal
    String name;
    @Email
    @NotEmpty
    @NonFinal
    String email;
}
