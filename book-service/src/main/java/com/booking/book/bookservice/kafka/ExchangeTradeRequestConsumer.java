package com.booking.book.bookservice.kafka;

import com.booking.book.bookservice.dto.ExchangeTradeRequestDto;
import com.booking.book.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExchangeTradeRequestConsumer {

    private final BookService bookService;

    @KafkaListener(
            topicPartitions = {@TopicPartition(topic = "exchange-topic", partitions = "0")},
            groupId = "book-group"
    )
    public void consumeTradeRequest(ExchangeTradeRequestDto tradeInfoRequest) {

        bookService.processExchange(tradeInfoRequest);
    }
}