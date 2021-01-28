package com.test.rabbitMQ.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.test.rabbitMQ.utils.rabbitMQConnection;

import java.io.IOException;

public class Provider
{
    // fanout model also called Publish/Subscribe model
    public static void main(String[] args) throws IOException
    {
        Connection connection = rabbitMQConnection.getConnection();
        Channel channel = connection.createChannel();

        // channel to declare exchange
        channel.exchangeDeclare("logs","fanout");

        channel.basicPublish("logs","", null, "fanout type message".getBytes());
        rabbitMQConnection.closeConnectionAndChannel(channel,connection);

    }
}
