package com.lc.spingbootandrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Lc
 * @Date 2023/4/28
 * @Description
 */
@Configuration
public class ConfromQueueConfig {
    public final static String EXCHANGE_CONF = "confirm.exchange";
    public static final String CONFORM_QUEUE = "confirm.queue";
    public static final String ROUT_KEY = "confirmKey";

    //交换机
    @Bean("ex")
    public DirectExchange exchange(){
        return new DirectExchange(EXCHANGE_CONF);
    }

    //队列
    @Bean("dl")
    public Queue dq(){
        return QueueBuilder.durable(CONFORM_QUEUE).build();
    }

    @Bean
    public Binding binding(@Qualifier("ex") DirectExchange exchange,
                          @Qualifier("dl") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(ROUT_KEY);
    }
}
