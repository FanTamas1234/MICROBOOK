package com.booking.book.exchangeservice.dto;

public record ExchangeRequestDto(
        String initiator_book_id,
        String receiver_book_id
) {
}
