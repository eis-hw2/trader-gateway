package com.example.trader.Core.Sender.Strategy;

import com.example.trader.Core.Sender.Sender;
import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Domain.Entity.Order;
import com.example.trader.Domain.Entity.Util.BrokerOrderPair;

import java.util.List;
import java.util.Map;

public abstract class InstantSender implements Sender{
    private List<Broker> brokers;
    /**
     *
     * @param traderSideUsername
     * @param orders
     * @return { $BrokerId: $Order }
     */
    @Override
    public abstract List<BrokerOrderPair> send(String traderSideUsername, List<Order> orders);

    public List<Broker> getBrokers() {
        return brokers;
    }

    public void setBrokers(List<Broker> brokers) {
        this.brokers = brokers;
    }
}
