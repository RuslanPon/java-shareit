package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "items", schema = "public")
@ToString
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "item_name", nullable = false)
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "is_available")
    private Boolean available;
    @OneToOne()
    private User owner;
    @OneToOne()
    private ItemRequest request;

    public Item(Item newItem) {
        this.setId(newItem.getId());
        this.setName(newItem.getName());
        this.setDescription(newItem.getDescription());
        this.setAvailable(newItem.getAvailable());
        this.setOwner(newItem.getOwner());
        this.setRequest(newItem.getRequest());
    }
}
