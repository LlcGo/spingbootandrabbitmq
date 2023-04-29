package com.lc.spingbootandrabbitmq.consumer;

import com.lc.spingbootandrabbitmq.config.ConfromQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author Lc
 * @Date 2023/4/29
 * @Description
 */
@Component
@Slf4j
public class ConsumerWarning {

    //报警
    @RabbitListener(queues = ConfromQueueConfig.WARING_QUEUE)
    public void warning(Message message){
        String msg = new String(message.getBody());
        log.info("报警队列收到的消息:{}",msg);
    }
}
