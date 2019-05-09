package com.example.trader.Core.BrokerSocket;

import com.example.trader.Domain.Broker;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

public class BrokerSocketContainer {
    private  BrokerSocketClient client;
    private Broker broker;

    public BrokerSocketContainer(Broker broker){
        this.broker = broker;
        try {
            client = new BrokerSocketClient(new URI("ws://" + broker.getUrl()));
        }
        catch(URISyntaxException e){
            System.out.println("[BrokerSocketContainer] " + " error");
            e.printStackTrace();
        }
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

    @Override
    protected void finalize() throws Throwable {
        close();
    }
}
