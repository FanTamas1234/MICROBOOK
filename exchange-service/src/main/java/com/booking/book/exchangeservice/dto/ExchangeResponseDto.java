package com.booking.book.exchangeservice.dto;

public record ExchangeResponseDto(
        String initiator_book_id,
        String receiver_book_id,
        String status,
        String created_at
) {
}
