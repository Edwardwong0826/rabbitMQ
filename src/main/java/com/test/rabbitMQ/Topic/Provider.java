package com.test.rabbitMQ.Topic;

import com.rabbitmq.client.BuiltinExchangeType;
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
        String exchangeName = "topics";

        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC);

        String routingKey = "user.save.findall";


        channel.basicPublish(exchangeName, routingKey,null, ("This is Topic model publish based on route key:" + routingKey + " send message" ).getBytes());
        rabbitMQConnection.closeConnectionAndChannel(channel, connection);
    }
}
