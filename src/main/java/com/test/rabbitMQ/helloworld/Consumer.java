package com.test.rabbitMQ.helloworld;

import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    // we can subscribe any salve node that have join with master node, just that master node must be up in normal clustering mode
    // it only copies the operational information of exchange and queue but not queue inside data

    // for high availability cluster we need use mirror cluster, to set policy in rabbitMQ machine/docker rabbitmqctl
    // set_policy ha-all '^' '{"ha-mode":"all","ha-sync-mode":"automatic"}' , this can refer to official site what kinds of parameter to set
    // once we set policy, above example will make all node be mirrored , so if master mode down, either node will promote to main node with all queue data
    // so we can still consume the message from new promoted main node, when finish consume the message it will be confirmed by node.
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5673);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        // consumer and provider queueDeclare parameter need to be same, else error
        // auto delete only will effective when the consumer connection is close
        channel.queueDeclare("hello", true, false, false, null);

        //消费消息
        channel.basicConsume("hello", true, new DefaultConsumer(channel) {

            // 最后一个parameter: 消息queue取出的消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("new String(body) = " + new String(body));
            }
        });

        //channel.close();
        //connection.close();
    }
}

