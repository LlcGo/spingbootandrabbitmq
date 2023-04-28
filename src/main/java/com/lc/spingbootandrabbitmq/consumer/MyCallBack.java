package com.lc.spingbootandrabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @Author Lc
 * @Date 2023/4/28
 * @Description 生产者到交换机的成功或者失败的回调
 */
@Component //第一步
@Slf4j
public class MyCallBack implements RabbitTemplate.ConfirmCallback {

    //将这个内部类注入实现了类
    @Resource //第二步
    private RabbitTemplate rabbitTemplate;

    @PostConstruct //第三步
    public void init(){
        rabbitTemplate.setConfirmCallback(this);
    }
    /**
     *
     * @param correlationData 1成功 回调的信息 2失败 也回调消息
     * @param flag 1是否成功
     * @param errMsg 1成功的时候没有返回消息， 2失败返回为什么失败的消息
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean flag, String errMsg) {
        String id = correlationData != null ? correlationData.getId() : "";
        if(flag){
          log.info("成功接收到消息 ID为:{}",id);
        }else {
            log.info("接收消息失败，失败的ID为:{}，原因是:{}",id,errMsg);
        }
    }
}
