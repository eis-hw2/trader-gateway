package com.example.trader.Core.Sender.Strategy.Split;

import com.example.trader.Core.Scheduler.OrderScheduler;
import com.example.trader.Core.Sender.Strategy.SplitSender;
import com.example.trader.Dao.DaoFactory;
import com.example.trader.Dao.Impl.OrderDao;
import com.example.trader.Domain.Order;
import com.example.trader.Domain.Wrapper.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/*
* Send the Order to one broker
* */
@Component
public class OneSender extends SplitSender {
    private String broker;

    @Autowired
    private DaoFactory daoFactory;
    @Autowired
    private OrderScheduler orderScheduler;

    private OrderDao orderDao;

    @Override
    public ResponseWrapper send(List<Order> orders) {
        orderDao = daoFactory.create(broker, OrderDao.class);
        orderScheduler.addSplitOrder(orders, orderDao);
        return null;
    }

    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }
}
