package com.test.rabbitMQ.workqueue;

import com.rabbitmq.client.*;
import com.test.rabbitMQ.utils.rabbitMQConnection;

import java.io.IOException;

public class Consumer2
{
    public static void main(String[] args) throws IOException
    {
        Connection connection = rabbitMQConnection.getConnection();
        final Channel channel = connection.createChannel();
        channel.basicQos(1);
        channel.queueDeclare("work",true,false,false,null);

        // to enable those free and faster consumer can consume message if other consumer slow or hang, 能者多劳
        channel.basicConsume("work",false, new DefaultConsumer(channel)
        {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("Consumer-2: " + new String(body));
                // manual confirm, parameter1: manual ack delivery tag, parameter2: is it open multiple message confirm
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });

    }
}
