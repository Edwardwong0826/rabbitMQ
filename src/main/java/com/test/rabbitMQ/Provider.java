package com.test.rabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Provider {

    //produce message
    @Test
    public void testSendMessage() throws IOException, TimeoutException
    {
        // create connection factory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // set connection to rabbitMQ host
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/wong");
        connectionFactory.setUsername("wong");
        connectionFactory.setPassword("guest");

        // get connection object
        Connection connection = connectionFactory.newConnection();

        // get connecting channel
        Channel channel = connection.createChannel();

        // bind channel to message queue
        // parameter 1: queue name, will auto create on rabbitMQ
        // parameter 2: to defined the queue is it durable/persistent, true means when MQ close it will save to hard drive
        // parameter 3: is it exclusive on the current queue, other may access this if is false else cannot
        // parameter 4: is it after consumption completed auto delete queue
        // parameter 5： optional added parameter
        channel.queueDeclare("hello", false, false, false, null);

        // publish message
        // parameter 1: exchange name
        // parameter 2: route key name
        // parameter 3: pass message extra setting
        // parameter 4: message itself

        channel.basicPublish("","hello", null, "hello rabbitMQ!".getBytes());
        channel.close();
        connection.close();
    }


}
