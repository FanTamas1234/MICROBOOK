package com.booking.book.exchangeservice.kafka;

import com.booking.book.exchangeservice.dto.ExchangeTradeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class ExchangeConsumer {

    private final Map<String, CompletableFuture<ExchangeTradeResponseDto>> tradeResponseFutures = new ConcurrentHashMap<>();

    @KafkaListener(
            topicPartitions = {@TopicPartition(topic = "book-topic", partitions = "0")},
            groupId = "exchange-group"
    )
    public void consumeTradeResponse(ExchangeTradeResponseDto tradeResponseDto) {

        String exchangeId = tradeResponseDto.exchangeId();

        CompletableFuture<ExchangeTradeResponseDto> futureTradeResponse = tradeResponseFutures.remove(exchangeId);
        if (futureTradeResponse != null) {
            futureTradeResponse.complete(tradeResponseDto);
        }
    }

    public CompletableFuture<ExchangeTradeResponseDto> getTradeResponse(String exchangeId) {

        CompletableFuture<ExchangeTradeResponseDto> futureTradeResponse = new CompletableFuture<>();
        tradeResponseFutures.put(exchangeId, futureTradeResponse);
        return futureTradeResponse;
    }
}
