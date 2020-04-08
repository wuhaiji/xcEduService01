package com.xuecheng.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author whj
 * @createTime 2020-02-23 17:39
 * @description
 **/
public class Producer05_headers {

    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    private static final String EXCHANGE_HEADERS_INFORM = "exchange_headers_inform";

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
            channel.exchangeDeclare(EXCHANGE_HEADERS_INFORM, BuiltinExchangeType.HEADERS);
            //交换机和队列绑定
            /**
             *  参数明细
             *  1、队列名称
             *  2、交换机名称
             *  3、路由key 在发布订阅模式中设置为“”
             */
            HashMap<String, Object> headersEmail = new HashMap<>();
            headersEmail.put("inform_type", "email");
            HashMap<String, Object> headersSms = new HashMap<>();
            headersSms.put("inform_type", "sms");
            channel.queueBind(QUEUE_INFORM_EMAIL, EXCHANGE_HEADERS_INFORM, "", headersEmail);
            channel.queueBind(QUEUE_INFORM_SMS, EXCHANGE_HEADERS_INFORM, "", headersSms);
            /**
             *
             * 1、交换机名称，不指令使用默认交换机名称 Default Exchange
             * 2、routingKey（路由key），根据key名称将消息转发到具体的队列，这里填写队列名称表示消息将发到此队列
             * 3、消息属性
             * 4、消息内容
             */
            //发送邮件消息
            String msg = "send EMAIL to user";
            /**
             * 参数明细
             * 1、交换机名称，不指令使用默认交换机名称 Default Exchange
             * 2、routingKey（路由key），根据key名称将消息转发到具体的队列，这里填写队列名称表示消
             息将发到此队列
             * 3、消息属性
             * 4、消息内容
             */
            String message = "email inform to user2" ;
            Map<String, Object> headers = new HashMap<>();
            headers.put("inform_type", "email");//匹配email通知消费者绑定的header
            //headers.put("inform_type", "sms");//匹配sms通知消费者绑定的header
            AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties.Builder();
            properties.headers(headers);
            //Email通知
            channel.basicPublish(EXCHANGE_HEADERS_INFORM, "", properties.build(), message.getBytes());

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException | TimeoutException e) {
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
