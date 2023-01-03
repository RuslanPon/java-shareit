package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.BadRequestException;
import ru.practicum.shareit.exceptions.ObjectNotFoundException;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService{
    private final ItemRequestRepository itemRequestRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    public ItemRequestDto getItemRequest(Long id, Long userId) {
        Optional<ItemRequest> itemRequest = itemRequestRepository.findById(id);
        Optional<User> owner = userRepository.findById(userId);
        if (owner.isEmpty()) {
            throw new ObjectNotFoundException(String.format("User with id=%d not found", userId));
        }
        if (itemRequest.isPresent()) {
            ItemRequestDto itemRequestDto = ItemRequestMapper.toItemRequestDto(itemRequest.get());
            return addCommentsToItem(itemRequestDto);
        } else {
            throw new ObjectNotFoundException("itemRequest with id not found" + id);
        }
    }

    @Override
    public List<ItemRequestDto> getAllItemRequest(Long userId) {
        Optional<User> owner = userRepository.findById(userId);
        if (owner.isEmpty()) {
            throw new ObjectNotFoundException(String.format("User with id=%d not found", userId));
        }
        return itemRequestRepository.findAllByRequesterId(userId).stream()
                .map(ItemRequestMapper::toItemRequestDto)
                .map(this::addCommentsToItem)
                .sorted(Comparator.comparing(ItemRequestDto::getCreated))
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemRequestDto> getAllItemRequestWithPagination(Long userId, Integer from, Integer size) {
        if (from != null && size != null) {
            if (size <= 0 || from < 0) {
                throw new BadRequestException("Incorrect size or from");
            }
        } else {
            return new ArrayList<>();
        }
        Optional<User> owner = userRepository.findById(userId);
        if (owner.isEmpty()) {
            throw new ObjectNotFoundException(String.format("User with id=%d not found", userId));
        }
        Pageable pageable = PageRequest.of(from / size, size, Sort.by("created").descending());
        return itemRequestRepository.findAllByRequesterIdIsNot(userId, pageable)
                .stream()
                .map(ItemRequestMapper::toItemRequestDto)
                .map(this::addCommentsToItem)
                .collect(Collectors.toList());
    }

    @Override
    public ItemRequestDto createItemRequest(ItemRequestDto itemRequestDto, Long userId) {
        ItemRequest newItemRequest = new ItemRequest();
        ItemRequestMapper.toItemRequest(newItemRequest, itemRequestDto);
        Optional<User> owner = userRepository.findById(userId);
        if (owner.isEmpty()) {
            throw new ObjectNotFoundException(String.format("User with id=%d not found", userId));
        } else {
            newItemRequest.setRequester(owner.get());
        }
        newItemRequest.setCreated(LocalDateTime.now());
        ItemRequest createdItemRequest = itemRequestRepository.save(newItemRequest);
        log.info("ItemRequest created" + createdItemRequest);
        return ItemRequestMapper.toItemRequestDto(createdItemRequest);
    }

    private ItemRequestDto addCommentsToItem(ItemRequestDto dto) {
        ItemRequest itemRequest = new ItemRequest();
        ItemRequestMapper.toItemRequest(itemRequest, dto);
        List<Item> items = itemRepository.findAllByRequestId(itemRequest.getId());
        if (items != null) {
            dto.setItems(items.stream()
                    .map(ItemMapper::toItemDto)
                    .collect(Collectors.toList()));
        } else {
            dto.setItems(new ArrayList<>());
        }
        return dto;
    }
}
