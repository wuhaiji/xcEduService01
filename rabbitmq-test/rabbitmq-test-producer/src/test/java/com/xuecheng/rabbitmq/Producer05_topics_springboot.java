package com.xuecheng.rabbitmq;

import com.xuecheng.rabbitmq.config.RabbitmqConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author whj
 * @createTime 2020-02-24 21:15
 * @description rabbit生产者
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class Producer05_topics_springboot {

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Test
    public void testSendByTopics(){
        for (int i=0;i<5;i++){
            String message = "sms email inform to user"+i;
            rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_TOPICS_INFORM, RabbitmqConfig.ROUTING_KEY_EMAIL,
                    message);
            System.out.println("Send Message is:'" + message + "'");
        }
    }
}
