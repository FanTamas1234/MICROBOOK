package com.booking.book.exchangeservice.kafka;

import com.booking.book.exchangeservice.dto.ExchangeTradeRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExchangeProducer {

    private final KafkaTemplate<String, ExchangeTradeRequestDto> kafkaTemplate;

    public void produceTradeRequest(ExchangeTradeRequestDto tradeInfoRequest) {

        Message<ExchangeTradeRequestDto> message = MessageBuilder
                .withPayload(tradeInfoRequest)
                .setHeader(KafkaHeaders.TOPIC, "exchange-topic")
                .setHeader(KafkaHeaders.PARTITION, 0)
                .build();

        kafkaTemplate.send(message);
    }
}