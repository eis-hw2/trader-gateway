package com.example.trader.Service.Impl;

import com.example.trader.Domain.Broker;
import com.example.trader.Service.BrokerConfigService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class BrokerConfigServiceImpl implements BrokerConfigService{
    public static CopyOnWriteArrayList<Broker> brokers = new CopyOnWriteArrayList<>();

    @Override
    public Broker addBroker(Broker brokerWrapper){
        brokers.add(brokerWrapper);
        return brokerWrapper;
    }

    @Override
    public boolean deleteBrokerById(String id){
        return brokers.removeIf(e->e.getId().equals(id));
    }

    @Override
    public List<Broker> getBroker(){
        return brokers;
    }

    @Override
    public Broker getBrokerById(String id) {
        for (Broker broker: brokers){
            if (broker.getId().equals(id)){
                return broker;
            }
        }
        return null;
    }
}
