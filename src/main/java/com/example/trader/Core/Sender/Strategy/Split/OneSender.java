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

import java.util.List;

/*
* Send the Order to One Broker
* */
@Component
public class OneSender extends SplitSender {

    @Autowired
    private DaoFactory daoFactory;
    @Autowired
    private OrderScheduler orderScheduler;

    @Override
    public int send(String traderSideUsername, List<Broker> brokers, List<Order> orders) {
        AbstractOrderDao orderDao = (AbstractOrderDao)daoFactory.create(brokers.get(0), orders.get(0).getType());
        return orderScheduler.addSplitOrder(traderSideUsername, orders, orderDao);
    }


}
