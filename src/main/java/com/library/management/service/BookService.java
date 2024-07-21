package com.library.management.service;

import com.library.management.controller.request.CreateBookRequest;
import com.library.management.controller.response.BookDetailResponse;
import com.library.management.mapper.BookMapper;
import com.library.management.model.Book;
import com.library.management.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public List<BookDetailResponse> getAllBooks() {
        logger.info("Fetching all books");
        return bookRepository.findAll().stream()
                .map(bookMapper::toDetailResponse)
                .toList();
    }

    public Optional<BookDetailResponse> getBookById(Long id) {
        logger.info("Fetching book by ID: {}", id);
        return bookRepository.findById(id).map(bookMapper::toDetailResponse);
    }

    @Transactional
    public BookDetailResponse addBook(CreateBookRequest request) {
        logger.info("Adding a new book with request: {}", request);
        final var book = bookMapper.toBook(request);
        var persistedBook = bookRepository.save(book);
        return bookMapper.toDetailResponse(persistedBook);
    }

    @Transactional
    public Optional<BookDetailResponse> updateBook(Long id, Book bookDetails) {
        logger.info("Updating book with ID: {}", id);
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
        logger.info("Deleting book with ID: {}", id);
        bookRepository.deleteById(id);
    }

    public List<BookDetailResponse> getBooksByGenre(String genre) {
        logger.info("Fetching books by genre: {}", genre);
        return bookRepository.findByGenre(genre).stream()
                .map(bookMapper::toDetailResponse)
                .toList();
    }

    public List<BookDetailResponse> getBooksByAuthor(String author) {
        logger.info("Fetching books by author: {}", author);
        return bookRepository.findByAuthorContainingIgnoreCase(author).stream()
                .map(bookMapper::toDetailResponse)
                .toList();
    }
}
