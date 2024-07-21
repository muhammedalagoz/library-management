package com.library.management.service;

import com.library.management.controller.request.CreateBookRequest;
import com.library.management.controller.response.BookDetailResponse;
import com.library.management.mapper.BookMapper;
import com.library.management.model.Book;
import com.library.management.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public List<BookDetailResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDetailResponse)
                .toList();
    }

    public Optional<BookDetailResponse> getBookById(Long id) {
        return bookRepository.findById(id).map(bookMapper::toDetailResponse);
    }

    @Transactional
    public BookDetailResponse addBook(CreateBookRequest request) {
        final var book = bookMapper.toBook(request);

        var persistedBook = bookRepository.save(book);

        return bookMapper.toDetailResponse(persistedBook);
    }

    @Transactional
    public Optional<BookDetailResponse> updateBook(Long id, Book bookDetails) {
        Book book = bookRepository.findByIdWithLock(id);
        if (book != null) {
            book.setTitle(bookDetails.getTitle());
            book.setAuthor(bookDetails.getAuthor());
            book.setIsbn(bookDetails.getIsbn());
            book.setPublishedDate(bookDetails.getPublishedDate());
            book.setQuantity(bookDetails.getQuantity());
            book.setGenre(bookDetails.getGenre());

            var persistedBook = bookRepository.save(book);
            return Optional.of(bookMapper.toDetailResponse(persistedBook));
        }
        return Optional.empty();
    }

    @Transactional
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<BookDetailResponse> getBooksByGenre(String genre) {
        return bookRepository.findByGenre(genre).stream()
                .map(bookMapper::toDetailResponse)
                .toList();
    }

    public List<BookDetailResponse> getBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author).stream()
                .map(bookMapper::toDetailResponse)
                .toList();
    }
}
