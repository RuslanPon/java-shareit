package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.Collection;

public interface ItemService {
    ItemDto addItem(Long userId, ItemDto itemDto);

    ItemDto editItem(Long userId, Long itemId, ItemDto itemDto);

    Collection<ItemDto> getAllItems(Long userId);

    ItemDto getItem(Long id, Long userId);

    Collection<ItemDto> searchItemByText(String text);

    CommentDto createComment(CommentDto commentDto, Long userId, Long itemId);
}
