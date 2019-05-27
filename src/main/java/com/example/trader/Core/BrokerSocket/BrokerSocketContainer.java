package com.example.trader.Core.BrokerSocket;

import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Domain.Entity.OrderBook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;

public class BrokerSocketContainer {
    private Logger logger = LoggerFactory.getLogger("BrokerSocketContainer");

    private BrokerSocketClient client;
    private Broker broker;

    public BrokerSocketContainer(Broker broker){

        logger.info("[BrokerSocketContainer.Constructor] " + broker.getWebSocket());
        this.broker = broker;
        init();
    }

    public void init(){
        try {
            client = new BrokerSocketClient(broker, this);
        }
        catch(URISyntaxException e){
            e.printStackTrace();
            logger.info("[BrokerSocketContainer] Error");
            logger.info("[BrokerSocketContainer] Reconnecting... ");
            init();
        }
    }

    public void send(byte[] bytes){
        client.send(bytes);
    }

    public void close(){
        client.close();
    }

    public Broker getBroker() {
        return broker;
    }

    public OrderBook getOrderBook(){
        return client.getOrderBook();
    }

    @Override
    protected void finalize() throws Throwable {
        if (client != null)
            client.close();
    }
}
