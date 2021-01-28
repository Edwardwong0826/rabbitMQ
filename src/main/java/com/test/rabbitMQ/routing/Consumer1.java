package com.test.rabbitMQ.routing;

import com.rabbitmq.client.*;
import com.test.rabbitMQ.utils.rabbitMQConnection;

import java.io.IOException;

public class Consumer1
{
    public static void main(String[] args) throws IOException
    {
        Connection connection = rabbitMQConnection.getConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "logs_direct";

        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
        String queue = channel.queueDeclare().getQueue();

        //based on the route key to bind queue and exchange
        channel.queueBind(queue, exchangeName,"error");

        channel.basicConsume(queue, true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("Consumer 1: " + new String(body));
            }
        });

    }
}
