package com.lc.spingbootandrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @Author Lc
 * @Date 2023/4/26
 * @Description
 */
//@Configuration
public class TtlQueueConfig {
    public static final String X_EXCHANGE = "X";
    public static final String Y_EXCHANGE = "Y";


    public static final String QUEUE_A = "QA";
    public static final String QUEUE_B = "QB";
    public static final String QUEUE_DEAD_QD = "QD";
    public static final String QUEUE_C = "QC";



    //交换机
    @Bean("xExchange")
    public DirectExchange xExchange(){
        return new DirectExchange(X_EXCHANGE);
    }

    @Bean("yExchange")
    public DirectExchange yExchange(){
        return new DirectExchange(Y_EXCHANGE);
    }



    //队列
    @Bean("queueA")
    public Queue qaQueue(){
        return QueueBuilder.durable(QUEUE_A)
                .ttl(10000)
                .deadLetterExchange(Y_EXCHANGE)
                .deadLetterRoutingKey("YD").build();
    }

    @Bean("queueB")
    public Queue qbQueue(){
        return QueueBuilder.durable(QUEUE_B)
                .ttl(40000)
                .deadLetterExchange(Y_EXCHANGE)
                .deadLetterRoutingKey("YD")
                .build();
    }

    //可以控制时间的队列
    @Bean("queueC")
    public Queue qcQueue(){
        return QueueBuilder.durable(QUEUE_C)
                .deadLetterRoutingKey("YD")
                .deadLetterExchange(Y_EXCHANGE)
                .build();
    }
    //死信
    @Bean("queueD")
    public Queue qdQueue(){
        return new Queue(QUEUE_DEAD_QD);
    }

    //绑定
    @Bean
    public Binding queuebBindingX(@Qualifier("queueB") Queue queue1B,
                                  @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queue1B).to(xExchange).with("XB");
    }

    @Bean
    public Binding queueABindingX(@Qualifier("queueA") Queue queue1A,
                                  @Qualifier("xExchange") DirectExchange yExchange){
        return BindingBuilder.bind(queue1A).to(yExchange).with("XA");
    }

    //声明死信队列 QD 绑定关系
    @Bean
    public Binding deadLetterBindingQAD(@Qualifier("queueD") Queue queueD,
                                        @Qualifier("yExchange") DirectExchange yExchange){
        return BindingBuilder.bind(queueD).to(yExchange).with("YD");
    }

    //qc与交换机绑定
    @Bean
    public Binding queCBindingQD(@Qualifier("queueC") Queue queueC,
                                 @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueC).to(xExchange).with("XC");
    }


}
