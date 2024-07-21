package com.library.management.mapper;

import com.library.management.controller.request.CreateBookRequest;
import com.library.management.controller.response.BookDetailResponse;
import com.library.management.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public Book toBook(CreateBookRequest request) {
        var book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setPublishedDate(request.getPublishedDate());
        book.setQuantity(request.getQuantity());
        book.setGenre(request.getGenre());
        return book;
    }

    public BookDetailResponse toDetailResponse(Book book) {
        return BookDetailResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .publishedDate(book.getPublishedDate())
                .quantity(book.getQuantity())
                .genre(book.getGenre())
                .build();
    }
}
