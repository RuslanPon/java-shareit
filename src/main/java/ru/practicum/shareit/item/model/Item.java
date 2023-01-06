package ru.practicum.shareit.item.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "items", schema = "public")
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(name = "item_name", nullable = false)
    String name;
    @Column(name = "description")
    String description;
    @Column(name = "is_available")
    Boolean available;
    @ManyToOne(optional = false)
    User owner;
    @ManyToOne()
    ItemRequest request;

    public Item(Item newItem) {
        this.setId(newItem.getId());
        this.setName(newItem.getName());
        this.setDescription(newItem.getDescription());
        this.setAvailable(newItem.getAvailable());
        this.setOwner(newItem.getOwner());
        this.setRequest(newItem.getRequest());
    }
}
