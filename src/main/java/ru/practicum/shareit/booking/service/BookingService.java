package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

public interface BookingService {
    BookingDto getBooking(Long id, Long userId);

    List<BookingDto> getAllBookingsByState(Long userId, String state, Integer from, Integer size);

    List<BookingDto> getAllBookingsByStateAndOwner(Long userId, String stringState, Integer from, Integer size);

    BookingDto createBooking(BookingDto bookingDto, Long userId);

    BookingDto updateBooking(Long bookingId, Long userId, Boolean approved);
}

