package ru.practicum.shareit.booking.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "bookings", schema = "public")
@ToString
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(name = "start_date")
    LocalDateTime start;
    @Column(name = "end_date")
    LocalDateTime end;
    @ManyToOne(optional = false)
    Item item;
    @ManyToOne(optional = false)
    User booker;
    @Enumerated(EnumType.STRING)
    Status status;

    public Booking(Booking newBooking) {
        this.setId(newBooking.getId());
        this.setBooker(newBooking.getBooker());
        this.setStart(newBooking.getStart());
        this.setEnd(newBooking.getEnd());
        this.setStatus(newBooking.getStatus());
        this.setItem(newBooking.getItem());
    }
}
