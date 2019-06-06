package com.example.trader.Core.MessageQueue;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.trader.Domain.Entity.OrderToSend;
import com.example.trader.Domain.Entity.Util.TaskConsumerCommand;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskProducer {
    /**
     * Queue FutureTask 用来生产未来的下单任务
     */
    private final static String QUEUE_NAME = "FutureTask";
    private final static Logger logger = LoggerFactory.getLogger("TaskProducer");
    private final static String MQ_HOST = "47.106.8.44";
    /**
     * Exchange 用来广播 Cancel 请求
     */
    private final static String EXCHANGE = "Cancel";

    private final static ConnectionFactory factory = getFactory();
    private final static Connection connection = getConnection(factory);

    public static boolean create(OrderToSend ots){
        logger.info("[TaskProducer.create] OrderToSend: " + JSON.toJSONString(ots));

        JSONObject message = new JSONObject();
        message.put("body", ots);
        message.put("type", TaskConsumerCommand.CREATE);
        return produce(message.toJSONString());
    }
    public static boolean produce(String message){
        logger.info(" [TaskProducer.produce] To send: " + message);
        try {
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            logger.info(" [TaskProducer.produce] Sent: " + message);
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean broadcast(String message){
        logger.info(" [TaskProducer.broadcast] To send: " + message);
        try {
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE, "fanout");

            channel.basicPublish(EXCHANGE, "", null, message.getBytes());
            logger.info(" [TaskProducer.broadcast] Sent: " + message);
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean cancel(String groupId){
        logger.info("[TaskProducer.cancel] GroupId: " + groupId);
        JSONObject message = new JSONObject();
        message.put("body", groupId);
        message.put("type", TaskConsumerCommand.CANCEL);
        return broadcast(message.toJSONString());
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
