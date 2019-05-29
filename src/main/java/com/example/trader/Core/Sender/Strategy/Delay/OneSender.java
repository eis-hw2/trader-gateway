package com.example.trader.Core.Sender.Strategy.Delay;

import com.example.trader.Core.Scheduler.OrderScheduler;
import com.example.trader.Core.Sender.Strategy.DelaySender;
import com.example.trader.Dao.Repo.AbstractOrderDao;
import com.example.trader.Dao.Factory.DaoFactory;
import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Domain.Entity.Order;

import java.util.Calendar;
import java.util.List;

/**
* Send the Order to One Broker
*/
public class OneSender extends DelaySender {

    private DaoFactory daoFactory;
    private OrderScheduler orderScheduler;

    public OneSender(DaoFactory daoFactory, OrderScheduler orderScheduler){
        this.daoFactory = daoFactory;
        this.orderScheduler = orderScheduler;
    }

    @Override
    public int send(String traderSideUsername, List<Broker> brokers, List<Order> orders) {
        // set the token when the scheduler is about to send the request
        AbstractOrderDao orderDao = (AbstractOrderDao)daoFactory.create(brokers.get(0), orders.get(0).getType());
        return orderScheduler.addSplitOrder(traderSideUsername, orders, orderDao, getStartTime(), getEndTime());
    }


}
