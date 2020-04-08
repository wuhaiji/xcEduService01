package com.xuecheng.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author whj
 * @createTime 2020-02-23 17:39
 * @description
 **/
public class Producer01_work {

    public static final String QUEUE = "hello world";

    public static void main(String[] args){
        //通过连接工厂连接mq
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        //设置虚拟机
        connectionFactory.setVirtualHost("/");
        Connection connection = null;
        try {
            //建立连接
            connection = connectionFactory.newConnection();
            //建立会话通道
            Channel channel = connection.createChannel();
            //采用默认交换机，声明队列
            //String queue,boolean durable, boolean exclusive, boolean autoDelete,Map<String, Object> arguments
            /**
             * 参数详细说明
             * 1、queue 队列名称
             * 2、durable 是否持久化
             * 3、exclusive 是否独占连接，队列只允许在该链接中访问，如果连接关闭后删除该队列，可用于创建临时的队列
             * 4、autoDelete 自动删除，队列不使用了就自动删除该队列，如果将此参数和exclusive设置为true，就可以实现临时队列
             * 5、arguments 队列参数,扩展参数，比如设置存活时间
             */
            channel.queueDeclare(QUEUE,true, false,false,null);
            //发送消息
            /**
             * 消息发布方法
             * param1：Exchange的名称，如果没有指定，则使用Default Exchange
             * param2:routingKey,根据路由key检消息转发到指定的消息队列，如果使用默认交换机，routingKey设置为队列的名称
             * param3:消息包含的属性
             * param4：消息体
             */
            //定义一个消息的内容
            String msg="hello world 2";
            channel.basicPublish("",QUEUE,null,msg.getBytes(StandardCharsets.UTF_8));
            System.out.println("send to mq:"+msg);
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }finally{
            if(connection != null)
            {
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
