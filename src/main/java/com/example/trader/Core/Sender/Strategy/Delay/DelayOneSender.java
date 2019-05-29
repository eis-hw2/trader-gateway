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
public class DelayOneSender extends DelaySender {

    private DaoFactory daoFactory;
    private OrderScheduler orderScheduler;

    public DelayOneSender(DaoFactory daoFactory, OrderScheduler orderScheduler){
        this.daoFactory = daoFactory;
        this.orderScheduler = orderScheduler;
    }

    @Override
    public Integer send(String traderSideUsername, List<Order> orders) {
        AbstractOrderDao orderDao = (AbstractOrderDao)daoFactory.create(getBrokers().get(0), orders.get(0).getType());
        return orderScheduler.addSplitOrder(traderSideUsername, orders, orderDao, getStartTime(), getEndTime());
    }


}
