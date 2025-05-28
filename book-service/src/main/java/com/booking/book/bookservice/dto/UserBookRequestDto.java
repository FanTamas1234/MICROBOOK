package com.booking.book.bookservice.dto;

public record UserBookRequestDto(
        String bookId,
        String condition,
        String comment
) {
}
