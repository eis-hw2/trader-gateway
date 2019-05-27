package com.example.trader.Service;

import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Domain.Entity.OrderBook;

import java.util.List;

public interface BrokerService {
    Broker create(Broker broker);

    boolean deleteById(Integer id);

    List<Broker> findAll();

    Broker findById(Integer id);

    OrderBook findOrderBookByBrokerId(Integer bid);
}
