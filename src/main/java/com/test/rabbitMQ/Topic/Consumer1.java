package com.test.rabbitMQ.Topic;

import com.rabbitmq.client.*;
import com.test.rabbitMQ.utils.rabbitMQConnection;

import java.io.IOException;

public class Consumer1
{
    public static void main(String[] args) throws IOException
    {
        Connection connection = rabbitMQConnection.getConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "topics";

        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC);
        String queue = channel.queueDeclare().getQueue();

        //based on the route key to bind queue and exchange, 动态通配形式routing key
        // * is only match one keyword , the routing key must be like user.anything
        // * can be added in before and after in routing key
        // can be combined with use with #
        channel.queueBind(queue, exchangeName,"user.*");

        channel.basicConsume(queue, true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("Consumer 1: " + new String(body));
            }
        });

    }
}
