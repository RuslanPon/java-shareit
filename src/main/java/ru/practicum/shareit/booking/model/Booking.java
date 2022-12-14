package ru.practicum.shareit.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

import lombok.ToString;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bookings", schema = "public")
@ToString
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "start_date")
    private LocalDateTime start;
    @Column(name = "end_date")
    private LocalDateTime end;
    @OneToOne()
    private Item item;
    @OneToOne()
    private User booker;
    @Enumerated(EnumType.STRING)
    private Status status;

    public Booking(Booking newBooking) {
        this.setId(newBooking.getId());
        this.setBooker(newBooking.getBooker());
        this.setStart(newBooking.getStart());
        this.setEnd(newBooking.getEnd());
        this.setStatus(newBooking.getStatus());
        this.setItem(newBooking.getItem());
    }
}
