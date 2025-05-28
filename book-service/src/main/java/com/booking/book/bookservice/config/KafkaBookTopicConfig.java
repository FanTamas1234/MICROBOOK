package com.booking.book.bookservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaBookTopicConfig {

    @Bean
    public NewTopic bookTopic() {
        return TopicBuilder
                .name("book-topic")
                .partitions(1)
                .build();
    }
}
