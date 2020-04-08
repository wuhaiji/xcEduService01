package com.xuecheng.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author whj
 * @createTime 2020-02-23 17:39
 * @description
 **/
public class Producer04_topic {

    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    private static final String EXCHANGE_TOPIC_INFORM="exchange_topic_inform";

    public static void main(String[] args) {
        //通过连接工厂连接mq
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        //设置虚拟机
        connectionFactory.setVirtualHost("/");
        Connection connection = null;
        Channel channel = null;

        try {
            //建立连接
            connection = connectionFactory.newConnection();
            //建立会话通道
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE_INFORM_SMS, true, false, false, null);
            channel.queueDeclare(QUEUE_INFORM_EMAIL, true, false, false, null);
            /**
             * 参数明细
             * 1、交换机名称
             * 2、交换机类型，fanout、topic、direct、headers
             * fanout:发布订阅 publish/scribe
             * topic:对应的topic模式
             * direct:对应的routing工作模式
             * headers:对应header工作模式
             */
            channel.exchangeDeclare(EXCHANGE_TOPIC_INFORM, BuiltinExchangeType.TOPIC);
            //交换机和队列绑定
            /**
             *  参数明细
             *  1、队列名称
             *  2、交换机名称
             *  3、路由key 在发布订阅模式中设置为“”
             */
            channel.queueBind(QUEUE_INFORM_EMAIL, EXCHANGE_TOPIC_INFORM, "inform.#.email.#");
            channel.queueBind(QUEUE_INFORM_SMS, EXCHANGE_TOPIC_INFORM, "inform.#.sms.#");
            /**
             *
             * 1、交换机名称，不指令使用默认交换机名称 Default Exchange
             * 2、routingKey（路由key），根据key名称将消息转发到具体的队列，这里填写队列名称表示消息将发到此队列
             * 3、消息属性
             * 4、消息内容
             */
            //发送邮件消息
            for (int i = 0; i < 2; i++) {
                String msg = "send EMAIL to user" + i;
                /**
                 * 参数明细
                 * 1、交换机名称，不指令使用默认交换机名称 Default Exchange
                 * 2、routingKey（路由key），根据key名称将消息转发到具体的队列，这里填写队列名称表示消
                 息将发到此队列
                 * 3、消息属性
                 * 4、消息内容
                 */
                channel.basicPublish(EXCHANGE_TOPIC_INFORM, "inform.email", null,msg.getBytes());
                System.out.println("Send Message is:'" + msg + "'");
            }
            //发送短信和邮件消息
            for (int i = 0; i < 2; i++) {
                String msg = "send  to user" + i;
                /**
                 * 参数明细
                 * 1、交换机名称，不指令使用默认交换机名称 Default Exchange
                 * 2、routingKey（路由key），根据key名称将消息转发到具体的队列，这里填写队列名称表示消
                 息将发到此队列
                 * 3、消息属性
                 * 4、消息内容
                 */
                channel.basicPublish(EXCHANGE_TOPIC_INFORM, "inform.sms.email", null, msg.getBytes());
                System.out.println("Send Message is:'" + msg + "'");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
