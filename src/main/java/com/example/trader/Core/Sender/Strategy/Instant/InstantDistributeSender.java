package com.example.trader.Core.Sender.Strategy.Instant;

import com.example.trader.Core.Sender.Strategy.InstantSender;
import com.example.trader.Dao.Factory.DaoFactory;
import com.example.trader.Dao.Repo.BrokerSideDao.Secured.AbstractOrderDao;
import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Domain.Entity.Order;
import com.example.trader.Domain.Entity.Util.BrokerOrderPair;
import com.example.trader.Service.BrokerSideUserService;

import java.util.ArrayList;
import java.util.List;

public class InstantDistributeSender extends InstantSender {

    private DaoFactory daoFactory;
    private BrokerSideUserService brokerSideUserService;

    public InstantDistributeSender(DaoFactory daoFactory, BrokerSideUserService brokerSideUserService){
        this.daoFactory = daoFactory;
        this.brokerSideUserService = brokerSideUserService;
    }

    @Override
    public List<BrokerOrderPair> send(String traderSideUsername, List<Order> orders) {


        // BrokerId, Order
        List<BrokerOrderPair> res = new ArrayList<>();

        int size = getBrokers().size();
        int curIndex = 0;
        for (Order order: orders){
            if (order.getTotalCount() == 0)
                continue;
            Broker curBroker = getBrokers().get(curIndex % size);
            String token = brokerSideUserService.getToken(traderSideUsername, curBroker.getId());
            // set the token when the scheduler is about to send the request
            AbstractOrderDao orderDao = (AbstractOrderDao)daoFactory.createWithToken(curBroker, orders.get(0).getType(), token);
            Order createdOrder = orderDao.create(order);
            createdOrder.setType(order.getType());
            res.add(new BrokerOrderPair(curBroker.getId(), createdOrder));
            ++curIndex;
        }

        return res;
    }
}
