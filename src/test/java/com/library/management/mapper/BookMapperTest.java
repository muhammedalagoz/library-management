package com.library.management.mapper;

import com.library.management.controller.request.CreateBookRequest;
import com.library.management.controller.response.BookDetailResponse;
import com.library.management.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookMapperTest {

    private BookMapper bookMapper;

    @BeforeEach
    void setUp() {
        bookMapper = new BookMapper();
    }

    @Test
    void toBook_ConvertsCreateBookRequestToBook() {
        CreateBookRequest request = new CreateBookRequest();
        request.setTitle("Test Title");
        request.setAuthor("Test Author");
        request.setIsbn("Test ISBN");
        request.setPublishedDate(LocalDate.of(2020, 1, 1));
        request.setQuantity(10);
        request.setGenre("Test Genre");

        Book book = bookMapper.toBook(request);

        assertEquals(request.getTitle(), book.getTitle());
        assertEquals(request.getAuthor(), book.getAuthor());
        assertEquals(request.getIsbn(), book.getIsbn());
        assertEquals(request.getPublishedDate(), book.getPublishedDate());
        assertEquals(request.getQuantity(), book.getQuantity());
        assertEquals(request.getGenre(), book.getGenre());
    }

    @Test
    void toDetailResponse_ConvertsBookToBookDetailResponse() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Title");
        book.setAuthor("Test Author");
        book.setIsbn("Test ISBN");
        book.setPublishedDate(LocalDate.of(2020, 1, 1));
        book.setQuantity(10);
        book.setGenre("Test Genre");

        BookDetailResponse response = bookMapper.toDetailResponse(book);

        assertEquals(book.getId(), response.getId());
        assertEquals(book.getTitle(), response.getTitle());
        assertEquals(book.getAuthor(), response.getAuthor());
        assertEquals(book.getIsbn(), response.getIsbn());
        assertEquals(book.getPublishedDate(), response.getPublishedDate());
        assertEquals(book.getQuantity(), response.getQuantity());
        assertEquals(book.getGenre(), response.getGenre());
    }
}
