package ru.practicum.shareit.item.repository;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class ItemRepositoryImpl implements ItemRepository {
    private final Map<Long, Item> items = new HashMap<>();
    private long idItemCounter = 0;

    @Override
    public Collection<Item> getAllItems() {
        return items.values();
    }

    @Override
    public Item getById(long id) {
        return items.get(id);
    }

    @Override
    public Item saveItem(Item item) {
        idItemCounter++;
        item.setId(idItemCounter);
        items.put(idItemCounter, item);
        return item;
    }

    @Override
    public Item editItem(Item item) {
        items.put(item.getId(), item);
        return items.get(item.getId());
    }

    @Override
    public boolean checkItems(long id) {
        return items.containsKey(id);
    }
}
