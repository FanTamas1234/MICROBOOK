package com.booking.book.exchangeservice.service;

import com.booking.book.exchangeservice.dto.ExchangeTradeRequestDto;
import com.booking.book.exchangeservice.dto.ExchangeTradeResponseDto;
import com.booking.book.exchangeservice.kafka.ExchangeConsumer;
import com.booking.book.exchangeservice.kafka.ExchangeProducer;
import com.booking.book.exchangeservice.mapper.ExchangeMapper;
import com.booking.book.exchangeservice.model.Exchange;
import com.booking.book.exchangeservice.model.TradeStatus;
import com.booking.book.exchangeservice.dto.ExchangeRequestDto;
import com.booking.book.exchangeservice.dto.ExchangeResponseDto;
import com.booking.book.exchangeservice.repository.ExchangeRepository;
import com.booking.book.exchangeservice.util.JWTAuthConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final ExchangeMapper exchangeMapper;
    private final ExchangeRepository exchangeRepository;
    private final JWTAuthConverter jwtAuthenticationConverter;
    private final ExchangeProducer exchangeProducer;
    private final ExchangeConsumer exchangeConsumer;

    public ExchangeResponseDto addExchange(

            ExchangeRequestDto request
    ) {

        Exchange entity = exchangeMapper.toExchangeDao(request);
        Exchange saved = exchangeRepository.save(entity);
        return exchangeMapper.toExchangeDto(saved);
    }

    public List<ExchangeResponseDto> getExchangesByBookId(String bookId) {

        return exchangeRepository.findAllByInitiatorBookIdEquals(bookId)
                .stream()
                .map(exchangeMapper::toExchangeDto)
                .toList();

    }

    @Transactional
    public ExchangeResponseDto processExchange(String trade) {

        UUID receiverId = UUID.fromString(jwtAuthenticationConverter.retrieveJwtFromSecurityContext().getClaim("sub"));

        Exchange exchange = exchangeRepository.findById(UUID.fromString(trade))
                .orElseThrow(() -> new RuntimeException(
                                "There is no exchange record in DB with such ID"
                        )
                );
        exchange.setReceiverId(receiverId);

        ExchangeTradeRequestDto tradeInfoRequest = exchangeMapper.toExchangeTradeRequestDto(exchange);
        exchangeProducer.produceTradeRequest(tradeInfoRequest);

        exchange.setStatus(TradeStatus.PENDING);
        exchangeRepository.save(exchange);

        try {
            ExchangeTradeResponseDto tradeInfo = exchangeConsumer.getTradeResponse(tradeInfoRequest.exchangeId())
                    .get(5, TimeUnit.SECONDS);

            if(TradeStatus.FAILED == TradeStatus.valueOf(tradeInfo.tradeStatus()))
                throw new RuntimeException("Exchange failed");
            exchange.setStatus(TradeStatus.CLOSED);


            return exchangeMapper.toExchangeDto(exchange);
        } catch (TimeoutException e) {
            throw new RuntimeException("Timeout when waiting respond from Kafka on message");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
