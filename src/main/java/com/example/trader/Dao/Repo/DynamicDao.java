package com.example.trader.Dao.Repo;

import com.example.trader.Domain.Entity.Broker;

public abstract class DynamicDao<K, V> {
    private Broker broker;
    public Broker getBroker() {
        return broker;
    }

    public void setBroker(Broker broker) {
        this.broker = broker;
    }

    public abstract V create(V  order);
}
