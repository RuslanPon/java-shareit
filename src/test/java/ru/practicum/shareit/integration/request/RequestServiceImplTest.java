package ru.practicum.shareit.integration.request;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.exceptions.BadRequestException;
import ru.practicum.shareit.exceptions.ObjectNotFoundException;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest(properties = "db.name=test1", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RequestServiceImplTest {
    private final ItemRequestService itemRequestService;

    @Test
    void testGetAllItemRequestPaginationWithZeroSize() {
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> itemRequestService.getAllItemRequestWithPagination(2L, 0, 0));

        Assertions.assertEquals("Incorrect size or from", exception.getMessage());
    }

    @Test
    void testGetAllItemRequestPaginationWithNegativeFrom() {
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> itemRequestService.getAllItemRequestWithPagination(2L, -1, 0));

        Assertions.assertEquals("Incorrect size or from", exception.getMessage());
    }


    @Test
    void testCreateItemRequestWithWrongUser() {
        ItemRequestDto itemRequestDto = ItemRequestDto.builder()
                .description("fdfdsfsd").build();
        final ObjectNotFoundException exception = Assertions.assertThrows(
                ObjectNotFoundException.class,
                () -> itemRequestService.createItemRequest(itemRequestDto, 100L));

        Assertions.assertEquals("User with id=100 not found", exception.getMessage());
    }
}
