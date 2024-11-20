package com.desafio.profissional.magic.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue addCardsQueue() {
        return new Queue("add-cards-queue", true);
    }

    @Bean
    public Queue importProcessQueue() {
        return new Queue("deck_import_queue", true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("magic-exchange");
    }

    @Bean
    public Binding addCardsBindingQueue(Queue addCardsQueue, TopicExchange exchange) {
        return BindingBuilder.bind(addCardsQueue).to(exchange).with("add-cards-routing-key");
    }

    @Bean
    public Binding importProcessBindingQueue(Queue importProcessQueue, TopicExchange exchange) {
        return BindingBuilder.bind(importProcessQueue).to(exchange).with("import-process-routing-key");
    }
}
