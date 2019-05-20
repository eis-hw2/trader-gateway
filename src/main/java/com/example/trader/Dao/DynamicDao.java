package com.example.trader.Dao;

import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Domain.Entity.Order;

public abstract class DynamicDao<K, V> {
    private Broker broker;
    public Broker getBroker() {
        return broker;
    }

    public void setBroker(Broker broker) {
        this.broker = broker;
    }

    public abstract V create(V  order);
    public abstract V getById(K  id);
}
