package com.booking.book.bookservice.dto;

public record ExchangeTradeRequestDto(

        String exchangeId,
        String initiatorId,
        String receiverId,
        String initiatorBookId,
        String receiverBookId
)
{


}
