package com.example.trader.Core.Sender.Strategy.Instant;

import com.example.trader.Core.Sender.Strategy.InstantSender;
import com.example.trader.Dao.Factory.DaoFactory;
import com.example.trader.Dao.Repo.AbstractOrderDao;
import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Domain.Entity.Order;
import com.example.trader.Service.BrokerSideUserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstantDistributeSender extends InstantSender {

    private DaoFactory daoFactory;
    private BrokerSideUserService brokerSideUserService;

    public InstantDistributeSender(DaoFactory daoFactory, BrokerSideUserService brokerSideUserService){
        this.daoFactory = daoFactory;
        this.brokerSideUserService = brokerSideUserService;
    }

    @Override
    public Map<String, String> send(String traderSideUsername, List<Broker> brokers, List<Order> orders) {
        Broker broker = brokers.get(0);
        String token = brokerSideUserService.getToken(traderSideUsername, broker.getId());

        // BrokerId, OrderId
        Map<String, String> res = new HashMap<>();

        int size = brokers.size();
        int curIndex = 0;
        for (Order order: orders){
            Broker curBroker = brokers.get(curIndex % size);
            // set the token when the scheduler is about to send the request
            AbstractOrderDao orderDao = (AbstractOrderDao)daoFactory.createWithToken(curBroker, orders.get(0).getType(), token);
            String orderId = orderDao.create(order);

            res.put(curBroker.getId().toString(), orderId);
            ++curIndex;
        }

        return res;
    }
}
