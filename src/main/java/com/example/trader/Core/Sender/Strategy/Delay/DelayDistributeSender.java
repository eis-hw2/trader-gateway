package com.example.trader.Core.Sender.Strategy.Delay;

import com.example.trader.Core.Scheduler.OrderScheduler;
import com.example.trader.Core.Sender.Strategy.DelaySender;
import com.example.trader.Dao.Repo.AbstractOrderDao;
import com.example.trader.Dao.Factory.DaoFactory;
import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Domain.Entity.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Send the Order to Different Brokers
*/
public class DelayDistributeSender extends DelaySender {

    private DaoFactory daoFactory;
    private OrderScheduler orderScheduler;

    public DelayDistributeSender(DaoFactory daoFactory, OrderScheduler orderScheduler){
        this.daoFactory = daoFactory;
        this.orderScheduler = orderScheduler;
    }

    @Override
    public Integer send(String traderSideUsername, List<Broker> brokers, List<Order> orders) {

        Map<Order, AbstractOrderDao> orderMap = new HashMap<>();

        int size = brokers.size();
        int curIndex = 0;
        for (Order order: orders){
            Broker curBroker = brokers.get(curIndex % size);
            // set the token when the scheduler is about to send the request
            AbstractOrderDao orderDao = (AbstractOrderDao)daoFactory.create(curBroker, orders.get(0).getType());
            orderMap.put(order, orderDao);
            ++curIndex;
        }
        return orderScheduler.addSplitOrder(traderSideUsername, orderMap, getStartTime(), getEndTime());

    }
}
