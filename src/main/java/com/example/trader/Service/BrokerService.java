package com.example.trader.Service;

import com.example.trader.Domain.Broker;

import java.util.List;

public interface BrokerService {
    public Broker addBroker(Broker broker);

    public boolean deleteBrokerById(String id);

    public List<Broker> getBroker();

    public Broker getBrokerById(String id);
}
