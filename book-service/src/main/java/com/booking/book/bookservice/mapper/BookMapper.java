package com.booking.book.bookservice.mapper;

import com.booking.book.bookservice.dto.BookRequestDto;
import com.booking.book.bookservice.dto.BookResponseDto;
import com.booking.book.bookservice.model.Book;
import org.springframework.stereotype.Service;

@Service
public class BookMapper {

    public Book toBook(BookRequestDto bookRequest) {

        return Book.builder()
                .title(bookRequest.title())
                .author(bookRequest.author())
                .isbn(bookRequest.isbn())
                .genre(bookRequest.genre())
                .year_published(Integer.valueOf(bookRequest.year_published()))
                .description(bookRequest.description())
                .build();

    }

    public BookResponseDto toBookResponseDto(Book persistedBook) {

        return new BookResponseDto(
                persistedBook.getTitle(),
                persistedBook.getAuthor(),
                persistedBook.getYear_published().toString()
        );

    }
}
