/*
 * Copyright 2025 IQKV Team.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.iqkv.incubator.quickstart.amqpconsuminghello.config;

import com.iqkv.incubator.quickstart.amqpconsuminghello.consumer.GreetingMessageConsumer;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
class AmqpConsumingConfig {
  private final AmqpQueuesProperties queues;

  @Bean
  SimpleMessageListenerContainer greeterMessageListenerContainer(
      final ConnectionFactory connectionFactory, final MessageListenerAdapter greeterMessageListenerAdapter) {
    final var container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.setQueueNames(queues.getGreeterQueue());
    container.setMessageListener(greeterMessageListenerAdapter);
    container.setAcknowledgeMode(AcknowledgeMode.AUTO);
    container.setDefaultRequeueRejected(false);
    return container;
  }

  @Bean
  MessageListenerAdapter greeterMessageListenerAdapter(final GreetingMessageConsumer consumer) {
    return new MessageListenerAdapter(consumer, "consume");
  }
}
