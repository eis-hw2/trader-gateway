package com.example.trader.Core.MessageQueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class TaskProducer {
    private final static String QUEUE_NAME = "FutureTask";
    private final static String MQ_HOST = "47.106.8.44";

    private final static ConnectionFactory factory = getFactory();
    private final static Connection connection = getConnection(factory);

    public static boolean produce(String message){
        System.out.println(" [Produce.produce] To send: " + message);
        try {
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [Produce.produce] Sent: " + message);
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("47.106.8.44");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel())
        {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }

    }

    private static ConnectionFactory getFactory(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(MQ_HOST);
        return factory;
    }

    private static Connection getConnection(ConnectionFactory factory){
        try {
            Connection connection = factory.newConnection();
            return connection;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
