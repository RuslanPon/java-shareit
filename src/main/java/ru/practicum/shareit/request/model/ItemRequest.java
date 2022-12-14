package ru.practicum.shareit.request.model;

import lombok.Data;
import lombok.ToString;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@ToString
@Table(name = "requests", schema = "public")
public class ItemRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String description;
    @OneToOne()
    @JoinColumn(name = "requester_id", referencedColumnName = "id")
    private User requester;
    private LocalDateTime created;
}
