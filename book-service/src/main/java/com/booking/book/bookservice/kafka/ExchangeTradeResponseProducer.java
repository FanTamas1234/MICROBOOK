package com.booking.book.bookservice.kafka;

import com.booking.book.bookservice.dto.ExchangeTradeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExchangeTradeResponseProducer {

    private final KafkaTemplate<String, ExchangeTradeResponseDto> kafkaTemplate;

    /**
     * Method for sending message to broker on requested exchange data to exchange-service topic.
     *
     * @param tradeResponseDto
     */
    public void produceExchangeTradeResponse(ExchangeTradeResponseDto tradeResponseDto) {

        Message<ExchangeTradeResponseDto> message = MessageBuilder
                .withPayload(tradeResponseDto)
                .setHeader(KafkaHeaders.TOPIC, "book-topic")
                .setHeader(KafkaHeaders.PARTITION, 0)
                .build();

        kafkaTemplate.send(message);
    }
}
