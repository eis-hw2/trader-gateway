package com.example.trader.Service;

import com.example.trader.Domain.Entity.Broker;

import java.util.List;

public interface BrokerService {

    List<Broker> findAll();

    Broker findById(Integer id);
}
