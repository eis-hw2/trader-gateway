package com.example.trader.Core.Sender;

import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Domain.Entity.Order;
import com.example.trader.Domain.Wrapper.ResponseWrapper;

import java.util.List;

public abstract class Sender {
    private List<Broker> brokers;

    public abstract Object send(String traderSideUsername, List<Order> orders);

    public List<Broker> getBrokers() {
        return brokers;
    }

    public void setBrokers(List<Broker> brokers) {
        this.brokers = brokers;
    }
}
