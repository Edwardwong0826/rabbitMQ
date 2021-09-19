package com.test.rabbitMQ.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.test.rabbitMQ.utils.rabbitMQConnection;

import java.io.IOException;

public class Provider
{
    // in this model, producer are sending the message by default ExChange, so we didn't set it/ or no need to set it
    // message produce by provider it only can be sent to ExChange, then ExChange decide send to which queue, producer can't decide
    public static void main(String[] args) throws IOException {
        Connection connection = rabbitMQConnection.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare("work",true,false,false,null);

        for(int i=0; i<=20; i++)
        {
            channel.basicPublish("", "work", null, (i +" work queue").getBytes());
        }

        rabbitMQConnection.closeConnectionAndChannel(channel, connection);

    }
}
