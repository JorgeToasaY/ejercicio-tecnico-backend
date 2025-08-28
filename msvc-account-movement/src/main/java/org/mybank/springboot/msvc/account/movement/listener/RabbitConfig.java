package org.mybank.springboot.msvc.account.movement.listener;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue customerQueue() {
        return new Queue("customerTopicQueue", true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("customerTopicExchange");
    }

    @Bean
    public Binding binding(Queue customerQueue, TopicExchange exchange) {
        return BindingBuilder.bind(customerQueue).to(exchange).with("customer.update");
    }
    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter converter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter);
        return template;
    }
}
