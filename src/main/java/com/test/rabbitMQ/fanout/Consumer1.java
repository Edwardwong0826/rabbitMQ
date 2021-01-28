package com.test.rabbitMQ.fanout;

import com.rabbitmq.client.*;
import com.test.rabbitMQ.utils.rabbitMQConnection;

import java.io.IOException;

public class Consumer1
{
    public static void main(String[] args) throws IOException
    {
        Connection connection = rabbitMQConnection.getConnection();
        Channel channel = connection.createChannel();

        // channel bind/declare exchange
        channel.exchangeDeclare("logs", "fanout");

        //temporary queue
        String queue = channel.queueDeclare().getQueue();

        // bind exchange and queue, because is channel connect exchange and queue
        channel.queueBind(queue, "logs", "");

        // consume message
        channel.basicConsume(queue, true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("Consumer 1： " + new String(body));
            }
        });
    }
}
