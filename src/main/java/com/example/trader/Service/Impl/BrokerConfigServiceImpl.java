package com.example.trader.Service.Impl;

import com.example.trader.Service.BrokerConfigService;
import com.example.trader.Util.Wrapper.BrokerWrapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class BrokerConfigServiceImpl implements BrokerConfigService{
    public static Integer id = 0;
    public static CopyOnWriteArrayList<BrokerWrapper> brokers = new CopyOnWriteArrayList<>();

    @Override
    public BrokerWrapper addBroker(BrokerWrapper brokerWrapper){
        synchronized (id) {
            brokerWrapper.setId(id++);
        }

        brokers.add(brokerWrapper);
        return brokerWrapper;
    }

    @Override
    public boolean deleteBrokerById(int id){
        return brokers.removeIf(e->e.getId() == id);
    }

    @Override
    public List<BrokerWrapper> getBroker(){
        return brokers;
    }
}
