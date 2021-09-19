package com.test.rabbitMQ.workqueue;

import com.rabbitmq.client.*;
import com.test.rabbitMQ.utils.rabbitMQConnection;

import java.io.IOException;

public class Consumer1
{
    public static void main(String[] args) throws IOException
    {
        Connection connection = rabbitMQConnection.getConnection();
        final Channel channel = connection.createChannel();
        channel.basicQos(1); // means everytime only consume 1 message
        // multiple consumer is consumed and choose by round rabin
        channel.queueDeclare("work",true,false,false,null);


        // autoAck if true : consumer will auto ack from rabbitMQ to consume message, if false will auto ack
        // to enable those free and faster consumer can consume message if other consumer slow or hang, 能者多劳
        channel.basicConsume("work",false, new DefaultConsumer(channel)
        {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                try
                {
                    Thread.sleep(2000);
                }
                catch
                (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Consumer-1: " + new String(body));
                // manual confirm, parameter1: confirm the queue which message, parameter2: is it open multiple message confirm
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });

    }
}
