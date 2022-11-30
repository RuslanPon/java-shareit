package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.exceptions.ObjectNotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {
    @Autowired
    private final ItemRepository itemRepository;
    @Autowired
    private final UserRepository userRepository;

    public Collection<ItemDto> getAllItems(long userId) {
        return itemRepository.getAllItems().stream()
                .filter(item -> Objects.equals(item.getOwner().getId(), userId))
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    public ItemDto getItem(long id) {
        return ItemMapper.toItemDto(itemRepository.getById(id));
    }

    public ItemDto addItem(long userId, ItemDto itemDto) {
        Item itemNew = new Item();
        ItemMapper.toItem(itemNew, itemDto);
        User owner = userRepository.findById(userId);
        if (owner == null) {
            throw new ObjectNotFoundException(String.format("User id = %d not exist", userId));
        } else {
            itemNew.setOwner(owner);
        }
        return ItemMapper.toItemDto(itemRepository.saveItem(itemNew));
    }

    public ItemDto editItem(long userId, long itemId, ItemDto itemDto) {
        Item itemEdit;
        if (itemRepository.checkItems(itemId)) {
            Item item = new Item(itemRepository.getById(itemId));
            if (!Objects.equals(item.getOwner().getId(), userId)) {
                throw new ObjectNotFoundException("This Item does not to belong this User");
            }
            ItemMapper.toItem(item, itemDto);
            itemEdit = itemRepository.editItem(item);
        } else {
            throw new ObjectNotFoundException(String.format("User id = %d not exist", userId));
        }
        return ItemMapper.toItemDto(itemEdit);
    }

    public Collection<ItemDto> searchItemByText(String text) {
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        return itemRepository.getAllItems().stream()
                .filter(item -> item.getDescription().toLowerCase().contains(text.toLowerCase()) && item.getAvailable())
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }
}
