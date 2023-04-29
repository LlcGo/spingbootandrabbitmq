package com.lc.spingbootandrabbitmq.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author Lc
 * @Date 2023/4/26
 * @Description
 */
//@Component
@Slf4j
public class ConsumerListenDead {

    @RabbitListener(queues = "QD")
    public void receiveMessage(Message message, Channel channel) throws Exception{
        String msg = new String(message.getBody());
        log.info("msg={}",msg);
        log.info("当前时间：{}，接收死信队列的消息：{}",new Date().toString(),msg);
    }
}
