package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Collection;

/**
 * TODO Sprint add-controllers.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private final ItemService itemService;

    @GetMapping
    public Collection<ItemDto> getAllItems(@RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Get all items for user with ID: " + userId);
        return itemService.getAllItems(userId);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItem(@PathVariable long itemId) {
        log.info("Get item for ID: " + itemId);
        return itemService.getItem(itemId);
    }

    @PostMapping
    public ResponseEntity<ItemDto> createItem(@RequestHeader("X-Sharer-User-Id") @Min(1) Long userId, @RequestBody @Valid ItemDto itemDto) {
        log.info("Creating item: " + itemDto + " for user with ID: " + userId);
        return ResponseEntity.status(HttpStatus.CREATED).body((itemService.addItem(userId, itemDto)));
    }

    @PatchMapping("/{itemId}")
    public ItemDto editItem(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable long itemId, @RequestBody ItemDto itemDto) {
        log.info("Editing item ID: " + itemId + " on " + itemDto + " for user with ID: " + userId);
        return itemService.editItem(userId, itemId, itemDto);
    }

    @GetMapping("/search")
    public Collection<ItemDto> searchItem(@RequestParam(name = "text") String text) {
        log.info("Searching the text in the descriptions of items: " + text);
        return itemService.searchItemByText(text);
    }


}
