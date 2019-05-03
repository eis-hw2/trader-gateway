package com.example.trader.Entity;

public class Order {

    public static short MARTKET_ORDER = 0;
    public static short LIMIT_ORDER = 1;
    public static short STOP_ORDER = 2;
    public static short CANCEL_ORDER = 3;

    public static short PENDING = 0;
    public static short DONE = 1;


    private long id;
    private short type;
    private Future future;
    private double unitPrice;
    private int volume;
    private short status;

    public Future getFuture() {
        return future;
    }

    public void setFuture(Future future) {
        this.future = future;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }
}
