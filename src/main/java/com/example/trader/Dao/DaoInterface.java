package com.example.trader.Dao;

import java.util.List;

/*
 * Get the data from Broker
 * or from local database
 */
public interface DaoInterface<K, V> {
    List<V> getAll(boolean local);
    V getById(boolean local);
    V create(V value);
    V modifyById(K id, V value);
    void deleteById(K id);
}
