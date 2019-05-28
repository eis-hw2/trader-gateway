package com.example.trader.Core.Sender.Strategy.Split;

import com.example.trader.Core.Scheduler.OrderScheduler;
import com.example.trader.Core.Sender.Strategy.SplitSender;
import com.example.trader.Dao.Repo.AbstractOrderDao;
import com.example.trader.Dao.Repo.DynamicDao;
import com.example.trader.Dao.Factory.DaoFactory;
import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Domain.Entity.Order;
import com.example.trader.Domain.Wrapper.ResponseWrapper;
import com.example.trader.Service.BrokerSideUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* Send the Order to Different Brokers
* */
@Component
public class DistributeSender extends SplitSender {

    @Autowired
    private DaoFactory daoFactory;
    @Autowired
    private OrderScheduler orderScheduler;

    @Override
    public int send(String traderSideUsername, List<Broker> brokers, List<Order> orders) {

        Map<Order, AbstractOrderDao> orderMap = new HashMap<>();

        int size = brokers.size();
        int curIndex = 0;
        for (Order order: orders){
            Broker curBroker = brokers.get(curIndex % size);
            AbstractOrderDao orderDao = (AbstractOrderDao)daoFactory.create(curBroker, orders.get(0).getType());
            orderMap.put(order, orderDao);
            ++curIndex;
        }
        return orderScheduler.addSplitOrder(traderSideUsername, orderMap);

    }
}
