package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

public interface ItemRepository {
    Collection<Item> getAllItems();

    Item getById(long id);

    Item saveItem(Item item);

    Item editItem(Item item);

    boolean checkItems(long id);
}
