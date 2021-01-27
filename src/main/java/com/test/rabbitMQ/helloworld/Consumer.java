package com.test.rabbitMQ.helloworld;

import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/wong");
        connectionFactory.setUsername("wong");
        connectionFactory.setPassword("guest");

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        // consumer and provider queueDeclare parameter need to be same, else error
        // auto delete only will effective when the consumer connection is close
        channel.queueDeclare("hello", true, false, false, null);

        //消费消息
        channel.basicConsume("hello", true, new DefaultConsumer(channel) {

            // 最后一个parameter: 消息queue取出的消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("new String(body) = " + new String(body));
            }
        });

        //channel.close();
        //connection.close();
    }
}

