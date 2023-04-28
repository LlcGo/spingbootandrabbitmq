package com.lc.spingbootandrabbitmq.consumer;

import com.lc.spingbootandrabbitmq.config.ConfromQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author Lc
 * @Date 2023/4/28
 * @Description
 */
@Component
@Slf4j
public class ConsumerConfirm {

    @RabbitListener(queues = ConfromQueueConfig.CONFORM_QUEUE)
    public void receiveMessage(Message message){
        String msg = new String(message.getBody());
        log.info("收到的消息是{}", msg);
    }
}
