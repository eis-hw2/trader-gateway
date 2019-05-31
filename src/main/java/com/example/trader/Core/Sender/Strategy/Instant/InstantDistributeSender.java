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
    public Map<String, Order> send(String traderSideUsername, List<Order> orders) {


        // BrokerId, Order
        Map<String, Order> res = new HashMap<>();

        int size = getBrokers().size();
        int curIndex = 0;
        for (Order order: orders){
            Broker curBroker = getBrokers().get(curIndex % size);
            String token = brokerSideUserService.getToken(traderSideUsername, curBroker.getId());
            // set the token when the scheduler is about to send the request
            AbstractOrderDao orderDao = (AbstractOrderDao)daoFactory.createWithToken(curBroker, orders.get(0).getType(), token);
            Order createdOrder = orderDao.create(order);

            res.put(curBroker.getId().toString(), createdOrder);
            ++curIndex;
        }

        return res;
    }
}
