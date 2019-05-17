package com.example.trader.Dao;

import com.example.trader.Domain.Entity.Order;

public abstract class Dao<K, V> {
    private String source;
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public abstract V create(V  order);
    public abstract V modify(K id, V value);
    public abstract void deleteById(K id);
    public abstract V getById(K  id);
}
