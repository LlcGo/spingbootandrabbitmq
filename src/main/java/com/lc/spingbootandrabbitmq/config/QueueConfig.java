package com.lc.spingbootandrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Lc
 * @Date 2023/4/29
 * @Description
 */
//@Configuration
public class QueueConfig {
    public static final String QUEUE_Y = "yyQueue";
    public static final String ROUTE_KEY = "youKey";
    public static final String EX_CHANGE = "defaulEx";

    @Bean("def")
    public DirectExchange directExchange(){
        return new DirectExchange(EX_CHANGE);
    }

    @Bean("defQueue")
    public Queue defQueue(){
        return QueueBuilder.durable(QUEUE_Y).maxPriority(10).build();
    }

    @Bean
    public Binding binding(@Qualifier("def") DirectExchange directExchange,
                           @Qualifier("defQueue") Queue queue){
        return BindingBuilder.bind(queue).to(directExchange).with(ROUTE_KEY);
    }
}
