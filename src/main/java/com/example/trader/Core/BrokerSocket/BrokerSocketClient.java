package com.example.trader.Core.BrokerSocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Domain.Entity.OrderBook;
import com.example.trader.Domain.Wrapper.SessionWrapper;
import com.example.trader.Service.Impl.WebSocketServiceImpl;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CopyOnWriteArraySet;

public class BrokerSocketClient extends WebSocketClient {

    public final static short INIT = 0;
    public final static short CONNECTING = 1;
    public final static short CONNECTED = 2;
    public final static short ERROR = 3;

    private int status = INIT;

    private OrderBook orderBook;
    private Integer brokerId;

    public BrokerSocketClient(Broker broker) throws URISyntaxException{

        super(new URI(broker.getWebSocket() + "/websocket/1"));
        brokerId = broker.getId();
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("[BrokerSocket.onOpen] "+ this.uri.toString() + " Connection Success");
    }

    @Override
    public void onMessage(String msg) {
        /*
        JSONObject jsonObject = JSONObject.parseObject(msg);
        JSONObject type = jsonObject.getJSONObject("type");
        String typeString = JsonHelper.jsonObjectToObject(type, String.class);
        switch (typeString){
            case "OrderBook":
                JSONObject orderbook = jsonObject.getJSONObject("body");
                OrderBook ob = JsonHelper.jsonObjectToObject(orderbook, OrderBook.class);
                orderBook = ob;
        }
        */
        System.out.println("[BrokerSocket.onMessage] " + this.uri.toString());

        orderBook = JSON.parseObject(msg, OrderBook.class);

        System.out.println(JSON.toJSONString(orderBook));

        WebSocketServiceImpl.staticBroadcastByBrokerId(msg, brokerId);
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        this.setStatus(ERROR);
        System.out.println("[BrokerSocket.onMessage]" + this.uri.toString() + " Connection Closed");
        while(!this.getReadyState().equals(WebSocket.READYSTATE.OPEN)){
            this.reconnect();
        }
    }

    @Override
    public void onError(Exception e) {
        System.out.println("[BrokerSocket.onError]" + this.uri.toString());
        e.printStackTrace();
    }

    public void reconnect(){
        init();
    }

    public void init(){
        this.setStatus(CONNECTING);
        System.out.println("[BrokerSocketContainer.init] " + this.uri + " Connecting");

        this.connect();
        while(!this.getReadyState().equals(WebSocket.READYSTATE.OPEN)){}

        this.setStatus(CONNECTED);
        System.out.println("[BrokerSocketContainer.init] " + this.uri + " Connected");

    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public OrderBook getOrderBook() {
        return orderBook;
    }

    public void setOrderBook(OrderBook orderBook) {
        this.orderBook = orderBook;
    }
}
