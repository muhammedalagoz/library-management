package com.library.management.controller;

import com.library.management.controller.request.CreateBookRequest;
import com.library.management.controller.response.BookDetailResponse;
import com.library.management.model.Book;
import com.library.management.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    void getAllBooksTest() throws Exception {
        given(bookService.getAllBooks()).willReturn(List.of(new BookDetailResponse()));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getBookByIdTest() throws Exception {
        given(bookService.getBookById(1L)).willReturn(Optional.of(new BookDetailResponse()));

        mockMvc.perform(get("/books/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void addBookTest() throws Exception {
        given(bookService.addBook(ArgumentMatchers.any(CreateBookRequest.class))).willReturn(new BookDetailResponse());

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void updateBookTest() throws Exception {
        given(bookService.updateBook(ArgumentMatchers.eq(1L), ArgumentMatchers.any(Book.class))).willReturn(Optional.of(new BookDetailResponse()));

        mockMvc.perform(put("/books/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBookTest() throws Exception {
        mockMvc.perform(delete("/books/{id}", 1))
                .andExpect(status().isNoContent());
    }
}
