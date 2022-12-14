package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findBookingsByBooker(User booker);

    List<Booking> findAllByBookerAndStartIsBeforeAndEndIsAfter(User owner, LocalDateTime currentTime, LocalDateTime currentTime1);

    List<Booking> findAllByBookerAndEndIsBeforeAndStatusIs(User owner, LocalDateTime currentTime, Status approved);

    List<Booking> findAllByBookerAndStartIsAfter(User owner, LocalDateTime currentTime);

    List<Booking> findAllByBookerAndStatusIs(User owner, Status waiting);
    @Query("select b from Booking b " +
            "where b.item.owner.id =:ownerId")
    List<Booking> findAllByOwnerOfItem(Long ownerId);

    @Query(" select b from Booking b " +
            "where (b.item.owner =:owner " +
            "and (:dateTime between b.start and b.end))")
    List<Booking> findAllByOwnerOfItemAndStartIsBeforeAndEndIsAfter(User owner, LocalDateTime dateTime);

    @Query(" select b from Booking b " +
            "where b.item.owner.id =:ownerId " +
            " and b.end<:dateTime " +
            "and b.status=:status")
    List<Booking> findAllByOwnerOfItemAndEndIsBeforeAndStatusIs(Long ownerId, LocalDateTime dateTime, Status status);

    @Query(" select b from Booking b " +
            "where b.item.owner.id =:ownerId " +
            "and b.start>:dateTime")
    List<Booking> findAllByOwnerOfItemAndStartIsAfter(Long ownerId, LocalDateTime dateTime);

    @Query(" select b from Booking b " +
            "where b.item.owner.id =:ownerId " +
            "and b.status = :status")
    List<Booking> findAllByOwnerOfItemAndStatusIs(Long ownerId, Status status);

    Booking findTop1ByItemAndEndIsBeforeOrderByEndDesc(Item item, LocalDateTime now);

    Booking findTop1ByItemAndStartIsAfterOrderByStartDesc(Item item, LocalDateTime now);

    Booking findTop1ByItemAndBookerAndEndIsBefore(Item item, User user, LocalDateTime now);
}
