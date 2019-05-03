package com.example.trader.Service;

import com.example.trader.Util.Wrapper.BrokerWrapper;

import java.util.List;

public interface BrokerConfigService {
    public BrokerWrapper addBroker(BrokerWrapper brokerWrapper);

    public boolean deleteBrokerById(int id);

    public List<BrokerWrapper> getBroker();
}
