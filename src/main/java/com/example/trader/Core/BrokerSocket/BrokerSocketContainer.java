package com.example.trader.Core.BrokerSocket;

import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Domain.Entity.OrderBook;

import java.net.URI;
import java.net.URISyntaxException;

public class BrokerSocketContainer {
    private  BrokerSocketClient client;
    private Broker broker;

    public BrokerSocketContainer(Broker broker){
        System.out.println("[BrokerSocketContainer.Constructor] " + broker.getWebSocket());
        this.broker = broker;

        try {
            client = new BrokerSocketClient(broker);
        }
        catch(URISyntaxException e){
            System.out.println("[BrokerSocketContainer] " + " error");
            e.printStackTrace();
        }
    }

    public void init(){
        client.init();
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
