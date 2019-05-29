package com.example.trader.Core.Sender.Strategy;

import com.example.trader.Core.Sender.Sender;
import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Domain.Entity.Order;

import java.util.List;
import java.util.Map;

public abstract class InstantSender extends Sender{
    /**
     *
     * @param traderSideUsername
     * @param brokers
     * @param orders
     * @return { $BrokerId: $OrderId }
     */
    @Override
    public abstract Map<String, String> send(String traderSideUsername, List<Broker> brokers, List<Order> orders);

}
