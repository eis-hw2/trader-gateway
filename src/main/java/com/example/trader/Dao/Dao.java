package com.example.trader.Dao;

public abstract class Dao<K, V> {

    private String source;

    public abstract V create(V value);

    public abstract V modify(K key, V value);

    public abstract V deleteById(K key);

    public abstract V getById(K key);

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
