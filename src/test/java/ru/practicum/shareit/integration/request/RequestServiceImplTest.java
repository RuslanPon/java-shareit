package ru.practicum.shareit.integration.request;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.exceptions.BadRequestException;
import ru.practicum.shareit.exceptions.ObjectNotFoundException;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest(properties = "db.name=test1", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RequestServiceImplTest {
    private final ItemRequestService itemRequestService;
    private final UserService userService;

    @Order(1)
    @Test
    void testCreateItemRequest() {
        UserDto ownerDto = UserDto.builder().name("User1").email("user1@mail.ru").build();
        userService.createUser(ownerDto);
        ItemRequestDto itemRequestDto = ItemRequestDto.builder().description("ItemRequest1").build();

        ItemRequestDto itemRequestDto1 = itemRequestService.createItemRequest(itemRequestDto, 1L);
        assertThat(itemRequestDto1.getId(), notNullValue());
        assertThat(itemRequestDto.getDescription(), equalTo(itemRequestDto1.getDescription()));
    }

    @Order(2)
    @Test
    void testGetItemRequest() {
        ItemRequestDto itemRequestDto1 = itemRequestService.getItemRequest(1L, 1L);
        assertThat(itemRequestDto1.getId(), notNullValue());
        assertThat(itemRequestDto1.getDescription(), equalTo("ItemRequest1"));
    }

    @Order(3)
    @Test
    void testGetAllItemRequest() {
        List<ItemRequestDto> itemRequestDtos = itemRequestService.getAllItemRequest(1L);
        assertThat(itemRequestDtos.size(), equalTo(1));
    }

    @Test
    void testGetAllItemRequestPaginationWithZeroSizeBadRequest() {
        List<ItemRequestDto> itemRequestDto = itemRequestService.getAllItemRequestWithPagination(1L, null, null);
        assertThat(itemRequestDto.size(), equalTo(0));
    }

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
    void testGetAllItemRequestPagination() {
        List<ItemRequestDto> itemRequestDto = itemRequestService.getAllItemRequestWithPagination(1L, 0, 1);
        assertThat(itemRequestDto.size(), equalTo(0));
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
