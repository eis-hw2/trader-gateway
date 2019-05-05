package com.example.trader.Entity;

public class Order {

    public Order(Order o){
        type = o.getType();
        status = o.getStatus();
        id = o.getId();
        futureId = o.getFutureId();
        position = o.getPosition();
        unitPrice = o.getUnitPrice();
        count = o.getCount();
        orderId = o.getOrderId();
    }

    public static final int BUYER = 0;
    public static final int SELLER = 1;
    public static enum Type{
        LIMIT,
        MARKET,
        STOP,
        CANCEL
    }

    public static enum Status{
        PENDING,
        DONE
    }

    Type type;
    Status status;
    String id;
    String futureId;
    int position;
    int unitPrice;
    int count;
    String orderId;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFutureId() {
        return futureId;
    }

    public void setFutureId(String futureId) {
        this.futureId = futureId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
