package com.lc.spingbootandrabbitmq.controller;

import com.lc.spingbootandrabbitmq.config.ConfromQueueConfig;
import com.lc.spingbootandrabbitmq.config.TtlQueueConfig;
import com.lc.spingbootandrabbitmq.config.TtlQueuePluginConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Lc
 * @Date 2023/4/26
 * @Description
 */
@Slf4j
@RestController
@RequestMapping("/ttl")
public class SendController {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMsg/{message}")
    public void sendMessage(@PathVariable("message") String message){
        log.info("当前时间是：{},发送一条消息给俩个ttl队列：{}",new Date().toString(), message);
        rabbitTemplate.convertAndSend("X","XA","消息来自ttl为10S的队列信息是"+ message);
        rabbitTemplate.convertAndSend("X","XB","消息来自ttl为40S的队列信息是"+ message);
    }

    //基于死信的延迟队列

//    @GetMapping("/sendMsg/{message}/{tts}")
//    public void sendMessageTtl(@PathVariable("message") String message,
//                               @PathVariable("tts") String tts){
//        log.info("当前时间是：{},发送一条消息给俩个ttl队列：{},设置时间为:{}",new Date().toString(), message,tts);
//        rabbitTemplate.convertAndSend("X","XC",message,msg -> {
//           msg.getMessageProperties().setExpiration(tts);
//           return msg;
//        });
//    }

    //基于插件的
    @GetMapping("/sendMsg/{message}/{tts}")
    public void sendMessageTtl(@PathVariable("message") String message,
                               @PathVariable("tts") Integer tts){
        log.info("当前时间是：{},发送一条消息给俩个ttl队列：{},设置时间为:{}",new Date().toString(), message,tts);
        rabbitTemplate.convertAndSend(TtlQueuePluginConfig.DELAYED_CHANGE,"delayed.route",message, msg -> {
            msg.getMessageProperties().setDelay(tts);
            return msg;
        });
    }

    @GetMapping("/conf/{messageConf}")
    public void sendConfrom(@PathVariable("messageConf") String message){
        log.info("发送的消息是{}",message);
        CorrelationData correlationData = new CorrelationData("1");
        rabbitTemplate
                .convertAndSend(ConfromQueueConfig.EXCHANGE_CONF,
                        ConfromQueueConfig.ROUT_KEY + 1,
                        "发送的消息是"+message ,correlationData);
    }

//    @GetMapping("/def/{message}")
//    public void sendMsg(){
//        log.info("发送到消息是");
//    }



}
