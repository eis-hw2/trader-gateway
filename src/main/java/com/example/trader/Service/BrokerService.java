package com.example.trader.Service;

import com.example.trader.Domain.Entity.Broker;

import java.util.List;

public interface BrokerService {
    public Broker addBroker(Broker broker);

    public boolean deleteBrokerById(Integer id);

    public List<Broker> getBroker();

    public Broker getBrokerById(Integer id);
}
