package com.library.management.service;

import com.library.management.controller.request.CreateBookRequest;
import com.library.management.controller.response.BookDetailResponse;
import com.library.management.mapper.BookMapper;
import com.library.management.model.Book;
import com.library.management.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAllBooksTest() {
        when(bookRepository.findAll()).thenReturn(List.of(new Book()));
        when(bookMapper.toDetailResponse(any(Book.class))).thenReturn(new BookDetailResponse());

        List<BookDetailResponse> result = bookService.getAllBooks();

        assertFalse(result.isEmpty());
    }

    @Test
    void getBookByIdTest() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(new Book()));
        when(bookMapper.toDetailResponse(any(Book.class))).thenReturn(new BookDetailResponse());

        Optional<BookDetailResponse> result = bookService.getBookById(1L);

        assertTrue(result.isPresent());
    }

    @Test
    void addBookTest() {
        when(bookMapper.toBook(any(CreateBookRequest.class))).thenReturn(new Book());
        when(bookRepository.save(any(Book.class))).thenReturn(new Book());
        when(bookMapper.toDetailResponse(any(Book.class))).thenReturn(new BookDetailResponse());

        BookDetailResponse result = bookService.addBook(new CreateBookRequest());

        assertNotNull(result);
    }

    @Test
    void updateBookTest() {
        Book existingBook = new Book();
        when(bookRepository.findByIdWithLock(1L)).thenReturn(existingBook);
        when(bookRepository.save(any(Book.class))).thenReturn(new Book());
        when(bookMapper.toDetailResponse(any(Book.class))).thenReturn(new BookDetailResponse());

        Optional<BookDetailResponse> result = bookService.updateBook(1L, new Book());

        assertTrue(result.isPresent());
    }

    @Test
    void getBooksByGenreTest() {
        when(bookRepository.findByGenre("Fiction")).thenReturn(List.of(new Book()));
        when(bookMapper.toDetailResponse(any(Book.class))).thenReturn(new BookDetailResponse());

        List<BookDetailResponse> result = bookService.getBooksByGenre("Fiction");

        assertFalse(result.isEmpty());
    }

    @Test
    void getBooksByAuthorTest() {
        when(bookRepository.findByAuthorContainingIgnoreCase("Author")).thenReturn(List.of(new Book()));
        when(bookMapper.toDetailResponse(any(Book.class))).thenReturn(new BookDetailResponse());

        List<BookDetailResponse> result = bookService.getBooksByAuthor("Author");

        assertFalse(result.isEmpty());
    }
}
