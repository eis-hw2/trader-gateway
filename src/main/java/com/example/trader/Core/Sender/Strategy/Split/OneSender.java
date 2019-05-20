package com.example.trader.Core.Sender.Strategy.Split;

import com.example.trader.Core.Scheduler.OrderScheduler;
import com.example.trader.Core.Sender.Strategy.SplitSender;
import com.example.trader.Dao.DynamicDao;
import com.example.trader.Dao.DaoFactory;
import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Domain.Entity.Order;
import com.example.trader.Domain.Wrapper.ResponseWrapper;
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
    public ResponseWrapper send(List<Broker> brokers, List<Order> orders) {
        DynamicDao orderDao = daoFactory.create(brokers.get(0), orders.get(0).getType());
        orderScheduler.addSplitOrder(orders, orderDao);
        return null;
    }


}
