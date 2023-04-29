package com.lc.spingbootandrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @Author Lc
 * @Date 2023/4/28
 * @Description
 */

//@Configuration
public class TtlQueuePluginConfig {
    //基于插件的交换机
    public static final String DELAYED_CHANGE = "delayed.exchange";


    //基于插件的队列
    public static final String DELAYED_QUEUE = "delayed.queue";


    //插件交换机
    @Bean
    public CustomExchange delExchange(){
        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put("x-delayed-type","direct");
        return new CustomExchange(DELAYED_CHANGE,"x-delayed-message",true,false,arguments);
    }

    //基于插件的队列
    @Bean("delQueue")
    public Queue delQueue(){
        return new Queue(DELAYED_QUEUE);
    }

    //绑定插件的
    @Bean
    public Binding delBinding(@Qualifier("delQueue") Queue delQueue,
                              @Qualifier("delExchange") CustomExchange delExchange){
        return BindingBuilder.bind(delQueue).to(delExchange).with("delayed.route").noargs();
    }
}
