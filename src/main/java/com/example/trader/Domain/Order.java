package com.example.trader.Domain;

import com.example.trader.Domain.Entity.Side;
import com.example.trader.Domain.Entity.Status;

public class Order {
    public static final String MARKET_ORDER = "MarketOrder";
    public static final String LIMIT_ORDER = "LimitOrder";
    public static final String STOP_ORDER = "StopOrder";
    public static final String CANCEL_ORDER = "CancelOrder";

    private String id;
    private int unitPrice;
    private Side side;
    private String marketDepthId;
    private Status status;
    private int count;

    public Order(Order o){
        id = o.getId();
        unitPrice = o.getUnitPrice();
        side = o.getSide();
        marketDepthId = o.getMarketDepthId();
        status = o.getStatus();
        count = o.getCount();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public String getMarketDepthId() {
        return marketDepthId;
    }

    public void setMarketDepthId(String marketDepthId) {
        this.marketDepthId = marketDepthId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
