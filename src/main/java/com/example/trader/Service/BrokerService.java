package com.example.trader.Service;

import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Domain.Entity.OrderBook;

import java.util.List;

public interface BrokerService {
    Broker addBroker(Broker broker);

    boolean deleteBrokerById(Integer id);

    List<Broker> getBroker();

    Broker getBrokerById(Integer id);

    OrderBook getOrderBookByBrokerId(Integer bid);
}
