package com.xuecheng.rabbitmq.handler;

import com.rabbitmq.client.Channel;
import com.xuecheng.rabbitmq.Config.RabbitmqConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author whj
 * @createTime 2020-02-24 21:47
 * @description
 **/

@Component
public class ReceiveHandler {

    @RabbitListener(queues={RabbitmqConfig.QUEUE_INFORM_EMAIL})
    public void receive_eamil(String msg, Message message, Channel channel){
        System.out.println(msg);
    }

}
