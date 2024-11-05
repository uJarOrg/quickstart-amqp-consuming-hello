package com.iqkv.incubator.quickstart.amqpconsuminghello.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
class AmqpConfiguration {

  private final AmqpQueuesProperties queues;

  @Bean
  AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
    return new RabbitAdmin(connectionFactory);
  }

  @Bean
  RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    return new RabbitTemplate(connectionFactory);
  }

  @Bean
  Queue greeterQueue() {
    return new Queue(queues.getGreeterQueue(),
        false, false, true);
  }

  @Bean
  TopicExchange greeterTopicExchange() {
    return new TopicExchange(queues.getGreeterExchange());
  }

  @Bean
  Declarables topicBindings(Queue greeterQueue, TopicExchange greeterTopicExchange) {
    return new Declarables(
        greeterQueue,
        greeterTopicExchange,
        BindingBuilder
            .bind(greeterQueue)
            .to(greeterTopicExchange).with("greeting")
    );
  }
}
