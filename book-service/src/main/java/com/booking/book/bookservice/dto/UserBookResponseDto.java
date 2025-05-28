package com.booking.book.bookservice.dto;

public record UserBookResponseDto(
        String bookId,
        String condition,
        String status,
        String addedAt,
        String comment
) {
}
