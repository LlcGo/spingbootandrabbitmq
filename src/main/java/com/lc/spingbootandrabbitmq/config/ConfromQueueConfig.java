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

    //备份交换机
    public static final String BACKUP_EX = "backup.exchange";
    //告警队列
    public static final String WARING_QUEUE = "warning.queue";
    //备份队列
    public static final String BACKUP_QUEUE = "backup.queue";
    //备份交换机 //fanout
    @Bean("backex")
    public FanoutExchange backex(){
        return new FanoutExchange(BACKUP_EX);
    }
    //交换机
    @Bean("ex")
    public DirectExchange exchange(){
        return ExchangeBuilder.directExchange(EXCHANGE_CONF)
                .durable(true).withArgument("alternate-exchange",BACKUP_EX).build();
    }

    //报警队列
    @Bean("bjqueue")
    public Queue bj(){
        return QueueBuilder.durable(WARING_QUEUE).build();
    }

    //报警队列f
    @Bean("bfqueue")
    public Queue bf(){
        return QueueBuilder.durable(BACKUP_QUEUE).build();
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

    //报警队列绑定
    @Bean
    Binding bd(@Qualifier("backex") FanoutExchange fanoutExchange,
                     @Qualifier("bjqueue") Queue queue){
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    //备份队列绑定
    @Bean
    Binding bdb(@Qualifier("backex") FanoutExchange fanoutExchange,
               @Qualifier("bfqueue") Queue queue){
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }
}
