package com.example.trader.Domain.Entity;

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
    private Type targetType;
    private String targetId;
    private String type;

    public Order(Order o){
        id = o.getId();
        unitPrice = o.getUnitPrice();
        side = o.getSide();
        marketDepthId = o.getMarketDepthId();
        status = o.getStatus();
        count = o.getCount();
        targetType = o.getTargetType();
        targetId = o.getTargetId();
        type = o.getType();
    }

    public Order(){}

    public Type getTargetType() {
        return targetType;
    }

    public void setTargetType(Type targetType) {
        this.targetType = targetType;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
