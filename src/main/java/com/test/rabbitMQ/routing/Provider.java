package com.test.rabbitMQ.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.test.rabbitMQ.utils.rabbitMQConnection;

import java.io.IOException;

public class Provider
{
    public static void main(String[] args) throws IOException
    {
        Connection connection = rabbitMQConnection.getConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "logs_direct";

        channel.exchangeDeclare(exchangeName, "direct");
        // in routing key we can specify the message go to the consumer exchange bind queue with its routing key only, only queue routing key
        // same with provider message routing key, consumer will received the message
        String routingKey = "warning";
        channel.basicPublish(exchangeName, routingKey,null, ("This is direct model publish based on route key:" + routingKey + " send message" ).getBytes());
        rabbitMQConnection.closeConnectionAndChannel(channel, connection);
    }
}
