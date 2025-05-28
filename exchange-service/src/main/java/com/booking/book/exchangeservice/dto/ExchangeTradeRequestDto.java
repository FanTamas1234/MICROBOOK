package com.booking.book.exchangeservice.dto;

public record ExchangeTradeRequestDto(

        String exchangeId,
        String initiatorId,
        String receiverId,
        String initiatorBookId,
        String receiverBookId
)
{


}
