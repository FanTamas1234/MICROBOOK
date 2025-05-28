package com.booking.book.bookservice.mapper;

import com.booking.book.bookservice.dto.BookRequestDto;
import com.booking.book.bookservice.dto.BookResponseDto;
import com.booking.book.bookservice.dto.UserBookRequestDto;
import com.booking.book.bookservice.dto.UserBookResponseDto;
import com.booking.book.bookservice.model.Book;
import com.booking.book.bookservice.model.BookStatus;
import com.booking.book.bookservice.model.Condition;
import com.booking.book.bookservice.model.UserBook;
import com.booking.book.bookservice.repository.BookRepository;
import com.booking.book.bookservice.repository.UserBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookMapper {

    private final BookRepository bookRepository;

    public Book toBook(BookRequestDto bookRequest) {

        return Book.builder()
                .title(bookRequest.title())
                .author(bookRequest.author())
                .isbn(bookRequest.isbn())
                .genre(bookRequest.genre())
                .yearPublished(Integer.valueOf(bookRequest.year_published()))
                .description(bookRequest.description())
                .build();

    }

    public BookResponseDto toBookResponseDto(Book persistedBook) {

        return new BookResponseDto(
                persistedBook.getTitle(),
                persistedBook.getAuthor(),
                persistedBook.getYearPublished().toString()
        );

    }

    public UserBook toUserBook(UUID userId, UserBookRequestDto requestDto) {

        Book book = bookRepository.findFirstByIsbn(requestDto.bookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        return UserBook.builder()
                .userId(userId)
                .bookId(book)
                .condition(Condition.valueOf(requestDto.condition()))
                //TODO: Добавить контролер для изменения статуса книги для обмена
                .status(BookStatus.READY_TO_TRADE)
                .comment(requestDto.comment())
                .build();
    }

    public UserBookResponseDto toUserBookResponseDto(UserBook book) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return new UserBookResponseDto(
                book.getBookId().getIsbn(),
                book.getCondition().name(),
                book.getStatus().name(),
                book.getAddedAt().format(formatter),
                book.getComment()
        );
    }
}
