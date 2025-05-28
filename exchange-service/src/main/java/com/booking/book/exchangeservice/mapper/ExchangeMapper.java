package com.booking.book.exchangeservice.mapper;

import com.booking.book.exchangeservice.dto.ExchangeTradeRequestDto;
import com.booking.book.exchangeservice.model.Exchange;
import com.booking.book.exchangeservice.model.TradeStatus;
import com.booking.book.exchangeservice.dto.ExchangeRequestDto;
import com.booking.book.exchangeservice.dto.ExchangeResponseDto;
import com.booking.book.exchangeservice.util.JWTAuthConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExchangeMapper {

    private final JWTAuthConverter jwtAuthConverter;

    public Exchange toExchangeDao(ExchangeRequestDto exchange) {

        UUID initiator_id = UUID.fromString(jwtAuthConverter.retrieveJwtFromSecurityContext().getClaim("sub"));

        return Exchange.builder()
                .initiatorId(initiator_id)
                .initiatorBookId(exchange.initiator_book_id())
                .receiverBookId(exchange.receiver_book_id())
                .status(TradeStatus.OPEN)
                .build();
    }

    public ExchangeResponseDto toExchangeDto(Exchange exchange) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return new ExchangeResponseDto(

                exchange.getInitiatorBookId(),
                exchange.getReceiverBookId(),
                exchange.getStatus().name(),
                exchange.getCreatedAt().format(formatter)
        );
    }

    public ExchangeTradeRequestDto toExchangeTradeRequestDto(

            Exchange exchange
    ) {
        return new ExchangeTradeRequestDto(

                exchange.getId().toString(),
                exchange.getInitiatorId().toString(),
                exchange.getReceiverId().toString(),
                exchange.getInitiatorBookId(),
                exchange.getReceiverBookId()
                );
    }
}
