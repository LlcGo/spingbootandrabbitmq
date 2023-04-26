package com.lc.spingbootandrabbitmq.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
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

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMsg/{message}")
    public void sendMessage(@PathVariable("message") String message){
        log.info("当前时间是：{},发送一条消息给俩个ttl队列：{}",new Date().toString(), message);
        rabbitTemplate.convertAndSend("X","XA","消息来自ttl为10S的队列信息是"+ message);
        rabbitTemplate.convertAndSend("X","XB","消息来自ttl为40S的队列信息是"+ message);
    }
}
