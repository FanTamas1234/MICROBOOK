package com.booking.book.bookservice.service;

import com.booking.book.bookservice.dto.BookResponseDto;
import com.booking.book.bookservice.mapper.BookMapper;
import com.booking.book.bookservice.model.Book;
import com.booking.book.bookservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.booking.book.bookservice.dto.BookRequestDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private BookMapper bookMapper;
    private BookRepository bookRepository;

    public BookResponseDto addBook(BookRequestDto bookRequest) {
        Book book = bookMapper.toBook(bookRequest);
        Book persistedBook = bookRepository.save(book);
        return bookMapper.toBookResponseDto(persistedBook);
    }

    public List<BookResponseDto> getAllBooks(
    ) {
        List<Book> tickets = bookRepository.findAll();
        return tickets.stream()
                .map(bookMapper::toBookResponseDto)
                .toList();
    }
}
