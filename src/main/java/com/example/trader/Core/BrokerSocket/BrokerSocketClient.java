package com.example.trader.Core.BrokerSocket;

import com.alibaba.fastjson.JSONObject;
import com.example.trader.Domain.Entity.OrderBook;
import com.example.trader.Util.JsonHelper;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class BrokerSocketClient extends WebSocketClient {

    public final static short INIT = 0;
    public final static short CONNECTING = 1;
    public final static short CONNECTED = 2;
    public final static short ERROR = 3;

    private int status = INIT;

    private OrderBook orderBook;

    public BrokerSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("[BrokerSocket.onOpen] "+ this.uri.toString() + " Connection Success");
    }

    @Override
    public void onMessage(String msg) {
        JSONObject jsonObject = JSONObject.parseObject(msg);
        JSONObject type = jsonObject.getJSONObject("type");
        String typeString = JsonHelper.jsonObjectToObject(type, String.class);
        switch (typeString){
            case "OrderBook":
                JSONObject orderbook = jsonObject.getJSONObject("body");
                OrderBook ob = JsonHelper.jsonObjectToObject(orderbook, OrderBook.class);
                orderBook = ob;
        }

        System.out.println("[BrokerSocket.onMessage] " + this.uri.toString() + msg);
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
