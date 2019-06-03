package com.example.trader.Domain.Entity.Util;

import com.example.trader.Domain.Entity.Order;

public class BrokerOrderPair {
    private int brokerId;
    private Order order;

    public BrokerOrderPair(int brokerId, Order order){
        this.brokerId = brokerId;
        this.order = order;
    }

    public int getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(int brokerId) {
        this.brokerId = brokerId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
