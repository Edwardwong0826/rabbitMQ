package com.test.rabbitMQ.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class rabbitMQConnection
{
    private static ConnectionFactory connectionFactory;

    static
    {
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/wong");
        connectionFactory.setUsername("wong");
        connectionFactory.setPassword("guest");

    }

    public static Connection getConnection()
    {  try
        {
         return connectionFactory.newConnection();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void closeConnectionAndChannel(Channel channel, Connection connection)
    {
        try
        {
            channel.close();
            connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
