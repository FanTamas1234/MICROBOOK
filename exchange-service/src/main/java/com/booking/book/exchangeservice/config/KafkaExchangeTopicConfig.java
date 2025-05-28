package com.booking.book.exchangeservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaExchangeTopicConfig {

    @Bean
    public NewTopic exchangeTopic() {
        return TopicBuilder
                .name("exchange-topic")
                .partitions(1)
                .build();
    }
}
