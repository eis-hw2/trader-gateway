package com.example.trader.Service.Impl;

import com.example.trader.Core.BrokerSocket.BrokerSocketContainer;
import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Service.BrokerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class BrokerServiceImpl implements BrokerService {
    public static CopyOnWriteArrayList<BrokerSocketContainer> brokerSocketContainers = new CopyOnWriteArrayList<>();

    @Override
    public Broker addBroker(Broker broker){
        BrokerSocketContainer brokerSocket = new BrokerSocketContainer(broker);
        brokerSocketContainers.add(brokerSocket);
        return broker;
    }

    @Override
    public boolean deleteBrokerById(String id){
        for (BrokerSocketContainer broker: brokerSocketContainers){
            if (broker.getBroker().getId().equals(id)){
                broker.close();
                brokerSocketContainers.remove(broker);
                break;
            }
        }
        return true;
    }

    @Override
    public List<Broker> getBroker(){
        return null;
    }

    @Override
    public Broker getBrokerById(String id) {
        return null;
    }
}
