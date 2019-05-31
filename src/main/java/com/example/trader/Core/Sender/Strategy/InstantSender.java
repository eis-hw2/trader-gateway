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
     * @param orders
     * @return { $BrokerId: $Order }
     */
    @Override
    public abstract Map<String, Order> send(String traderSideUsername, List<Order> orders);

}
